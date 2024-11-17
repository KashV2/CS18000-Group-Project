import javax.xml.crypto.Data;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler implements Runnable {
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
                }
                else if (menuResponse == 2) {
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


                                                User user1 = db.getUserFromProfileName(addUser);

                                                String ans = db.friendUser(user.getLoginUsername(), user1.getLoginUsername());

                                                clientWriter.println(ans);
                                            }
                                            else if (menuResponse3 == 2) {

                                                String removeAddedUser = searchName;

                                                User user1 = db.getUserFromProfileName(removeAddedUser);

                                                user.getProfile().removeFriend(removeAddedUser);
                                                user1.getProfile().removeFriend(user.getProfile().getName());

                                                clientWriter.println("Succesfully unfriended");
                                            }
                                            break;
                                        case 2:
                                            int menuResponse4 = Integer.parseInt(clientReader.readLine());

                                            if (menuResponse4 == 1) {
                                                String blockUser = searchName;


                                                User user1 = db.getUserFromProfileName(blockUser);

                                                String ans = db.blockUser(user.getLoginUsername(), user1.getLoginUsername());

                                                clientWriter.println(ans);


                                            }
                                            else if (menuResponse4 == 2) {

                                                String removeblockedUser = searchName;

                                                User user1 = db.getUserFromProfileName(removeblockedUser);

                                                user.getProfile().removeBlock(removeblockedUser);
                                                user1.getProfile().removeBlock(user.getProfile().getName());


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
                                            }
                                            clientWriter.println((String)null);

                                            //Message Loop
                                            inDM = true;
                                            while (true) {
                                                String sentMessage = clientReader.readLine();
                                                if (sentMessage.equals("/bye")) {
                                                    inDM = false;
                                                    break;
                                                }

                                                //Process of saving message and sending to receiver
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
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
