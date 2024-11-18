import javax.xml.crypto.Data;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The ClientHandler class. A thread created from Server for each Client to handle interaction between
 * the client, server, and databases.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @author Abhinav Kotamreddy
 * @author Kashyap Vallur
 * @version November 17, 2024
 */

public class ClientHandler implements Runnable, ClientHandlerInterface {
    private Socket client;
    private BufferedReader clientReader;
    private PrintWriter clientWriter;
    private User user;
    private Profile profile;
    private boolean inDM;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            clientWriter = new PrintWriter(client.getOutputStream(), true);
            Database db = Server.getDatabase();
            ChatDatabase chatDb = Server.getChatDatabase();

            //Signing In Loop
            boolean signedIn = false;
            while (!signedIn) {
                String loginUsername = clientReader.readLine();
                String password = clientReader.readLine();
                int signInResponse = Integer.parseInt(clientReader.readLine());

                String errorMessage = null;
                switch (signInResponse) {
                    case 1:
                        //Sign in request to database
                        //Check correct username and password
                        //On fail send information to the client of how we failed
                        //If we could sign in, send information back to the client that we successfully signed in
                        if (db.getUser(loginUsername) == null) {
                            errorMessage = "Username does not exist";
                            signedIn = false;
                        } else {
                            user = db.getUser(loginUsername);
                            if (!user.getPassword().equals(password)) {
                                errorMessage = "Password is incorrect";
                                signedIn = false;
                            } else {
                                //Signed in successfully
                                profile = user.getProfile();
                                signedIn = true;
                            }
                        }
                        break;
                    case 2:
                        //Create new account request to database
                        //Check if the username already exists but same password as another user is okay
                        //On fail send information to the client of how we failed
                        //If we could create an account, create it on the database, sign in on the client,
                        //and send information back to the client on the successful creation
                        if (db.nameAlreadyExists(loginUsername)) {
                            errorMessage = "Username already exists";
                            signedIn = false;
                        } else {
                            profile = new Profile(loginUsername, "",
                                new ArrayList<>(), new ArrayList<>());
                            user = new User(loginUsername, password, profile);
                            db.addUser(user);
                            //User added successfully
                            signedIn = true;
                        }
                        break;
                    default:
                        //Just keep it I think kids would get mad if this doesn't exist
                }
                clientWriter.println(signedIn);
                clientWriter.println(errorMessage);
            }

            while (true) {
                int menuResponse = Integer.parseInt(clientReader.readLine());
                if (menuResponse == 1) {
                    //Edit User Profile
                    //Send info about self
                    clientWriter.printf("Name: %s\n", profile.getName());
                    clientWriter.printf("Description: %s\n", profile.getDescription());
                    clientWriter.printf("Friends: %s\n", profile.getFriends());
                    clientWriter.printf("Blocked: %s\n", profile.getBlockedUsers());

                    int menuResponse2 = Integer.parseInt(clientReader.readLine());
                    switch (menuResponse2) {
                        case 1:

                            String newName = clientReader.readLine();
                            user.getProfile().setName(newName);
                            clientWriter.println("Name Changed Successfully");

                            break;
                        case 2:
                            String newDescription = clientReader.readLine();
                            user.getProfile().setDescription(newDescription);
                            clientWriter.println("Description Changed Successfully");
                            break;
                    }
                    db.saveUsers();
                } else if (menuResponse == 2) {
                    //Search & View User Profile
                    ArrayList<User> usersList = db.getUsers();
                    String searchName = clientReader.readLine();
                    boolean userFound = false;
                    for (int i = 0; i < usersList.size(); i++) {
                        if (searchName.equals(usersList.get(i).getLoginUsername())) {
                            Profile profile = usersList.get(i).getProfile();
                            clientWriter.printf("Name: %s\n", profile.getName());
                            clientWriter.printf("Description: %s\n", profile.getDescription());
                            clientWriter.printf("Friends: %s\n", profile.getFriends());
                            userFound = true;

                            int menuResponse2 = Integer.parseInt(clientReader.readLine());
                            switch (menuResponse2) {
                                case 1:
                                    int menuResponse3 = Integer.parseInt(clientReader.readLine());


                                    if (menuResponse3 == 1) {
                                        String addUser = searchName;


                                        User user1 = db.getUser(addUser);

                                        String ans = db.friendUser(user.getLoginUsername(), user1.getLoginUsername());
                                        db.saveUsers();

                                        clientWriter.println(ans);
                                    } else if (menuResponse3 == 2) {

                                        String removeAddedUser = searchName;

                                        User user1 = db.getUser(removeAddedUser);

                                        user.getProfile().removeFriend(removeAddedUser);
                                        user1.getProfile().removeFriend(user.getProfile().getName());
                                        db.saveUsers();

                                        clientWriter.println("Succesfully unfriended");
                                    }
                                    break;
                                case 2:
                                    int menuResponse4 = Integer.parseInt(clientReader.readLine());

                                    if (menuResponse4 == 1) {
                                        String blockUser = searchName;


                                        User user1 = db.getUser(blockUser);

                                        String ans = db.blockUser(user.getLoginUsername(), user1.getLoginUsername());
                                        db.saveUsers();

                                        clientWriter.println(ans);


                                    } else if (menuResponse4 == 2) {

                                        String removeblockedUser = searchName;

                                        User user1 = db.getUser(removeblockedUser);

                                        user.getProfile().removeBlock(removeblockedUser);
                                        user1.getProfile().removeBlock(user.getProfile().getName());
                                        db.saveUsers();


                                        clientWriter.println("Succesfully unblocked");
                                    }
                                    break;
                                case 3:
                                    //Message User
                                    User receivingUser = db.getUser(searchName);
                                    //Deny DM if you or receiver is blocking each other
                                    if (user.getProfile().isBlocked(receivingUser.getProfile()) ||
                                        receivingUser.getProfile().isBlocked(user.getProfile())) {
                                        clientWriter.println(false);
                                        break;
                                    } else {
                                        clientWriter.println(true);
                                    }

                                    //Creating or retrieving chat object
                                    Chat chat = new Chat(user.getLoginUsername(), receivingUser.getLoginUsername());
                                    if (chatDb.chatRegistered(chat)) {
                                        chat = chatDb.getChat(chat);
                                    } else {
                                        chatDb.addChat(chat);
                                    }

                                    //Loading Messages
                                    ArrayList<String> messageHistory = chat.getMessages();
                                    for (String message : messageHistory) {
                                        clientWriter.println(message);
                                        System.out.println("This is from loading: " + message);
                                    }
                                    clientWriter.println("<~!||NULL||!~>");

                                    //Message Loop
                                    inDM = true;
                                    while (true) {
                                        String sentMessage = clientReader.readLine();
                                        if (sentMessage.charAt(0) == '/') {
                                            //Secret Exit Message
                                            if (sentMessage.equals("/bye")) {
                                                inDM = false;
                                                clientWriter.println("/bye"); //Send this message to ourselves
                                                break;
                                            }
                                            //Secret Deletion Code
                                            sentMessage = sentMessage.substring(1);
                                            //Assume input is correct because this is just temporary
                                            int index = Integer.parseInt(sentMessage);
                                            if (index < 0 || index >= messageHistory.size()) continue;
                                            int loginNameLength = user.getLoginUsername().length();
                                            if (messageHistory.get(index).length() >= loginNameLength) {
                                                //Check if we own the message we are trying to delete
                                                if (messageHistory.get(index).substring(0, loginNameLength).equals(user.getLoginUsername())) {
                                                    chat.removeMessage(index);
                                                    chatDb.saveChats();
                                                }
                                            }
                                            continue;
                                        }

                                        //Process of saving message and sending to receiver
                                        sentMessage = user.getLoginUsername() + ": " + sentMessage;
                                        chat.addMessage(sentMessage);
                                        chatDb.saveChats();
                                        //Send to Receiving Client if they are online (and in DM's too)
                                        ArrayList<ClientHandler> clientHandlers = Server.getClientHandlers();
                                        for (ClientHandler onlineClient : clientHandlers) {
                                            if (!onlineClient.inDM) continue;
                                            if (onlineClient.user.equalsUsername(receivingUser.getLoginUsername())) {
                                                onlineClient.clientWriter.println(sentMessage);
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                default: //Back
                            }
                            break;
                        }
                    }
                    if (!userFound) {
                        clientWriter.println("");
                    }
                } else if (menuResponse == 3) {
                    //Exit
                    if (client != null) client.close();
                    Server.removeClient(this, client);
                    break;
                }
            }
        } catch (Exception e) {
            //Client probably closed their program abruptly
            try {
                if (client != null) client.close();
                Server.removeClient(this, client);
            } finally {
                return;
            }
        }
    }
}
