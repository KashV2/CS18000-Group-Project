import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket server = createServer();
        if (server == null) return;
        System.out.println("Connected to Server!");

        Scanner scanner = new Scanner(System.in);
        BufferedReader[] serverReader = new BufferedReader[1]; //Use index zero for access
        PrintWriter[] serverWriter = new PrintWriter[1]; //Use index zero for access
        createServerStreams(server, serverReader, serverWriter);

        boolean signedIn = false;
        while(!signedIn) {
            //Validate signInResponse
            int signInResponse = 0;
            while (true) {
                System.out.println("Do you want to sign in or create a new account?\n1 Sign in\n2 Create new account");
                try {
                    signInResponse = Integer.parseInt(scanner.nextLine());
                    if (signInResponse != 1 && signInResponse != 2) {
                        throw new RuntimeException();
                    }
                    break;
                } catch (RuntimeException e) {
                    System.out.println("Invalid input!");
                }
            }

            //Login input
            System.out.println("Please enter your username: ");
            String loginUsername = scanner.nextLine();

            System.out.println("Please enter your password: ");
            String password = scanner.nextLine();

            //Send login or create new account request to server (must be in this order)
            serverWriter[0].println(loginUsername);
            serverWriter[0].println(password);
            serverWriter[0].println(signInResponse);

            String errorMessage = null;
            try {
                signedIn = Boolean.parseBoolean(serverReader[0].readLine());
                errorMessage = serverReader[0].readLine();

                if (!signedIn) {
                    if (errorMessage != null) {
                        System.out.println(errorMessage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //AFTER LOGGING IN -- Main Menu / Loop
        //Choose between editing self or searching & viewing other profile
        boolean running = true;
        while (running) {
            int menuResponse = 0;
            while (true) {
                System.out.println("Choose between the following:\n1. Edit User Profile" +
                    "\n2. Search & View Users" +
                    "\n3. Exit");
                try {
                    menuResponse = Integer.parseInt(scanner.nextLine());
                    if (menuResponse < 1 || menuResponse > 3) throw new RuntimeException();
                    break;
                } catch (RuntimeException e) {
                    System.out.println("Invalid input!");
                }
            }

            //Send Menu Selection to Server
            serverWriter[0].println(menuResponse);

            //Handle Menu Selection
            if (menuResponse == 1) {
                //Edit User Profile

                System.out.println("Would you like to change the name(1) or description(2)?");
                int ans = Integer.parseInt(scanner.nextLine());
                serverWriter[0].println(ans);

                switch (ans) {
                    case 1:

                        System.out.println("Please enter your new name: ");
                        String newName = scanner.nextLine();
                        serverWriter[0].println(newName);
                        try {
                            System.out.println(serverReader[0].readLine());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        System.out.println("Please enter your new description: ");
                        String newDescription = scanner.nextLine();
                        serverWriter[0].println(newDescription);
                        try {
                            System.out.println(serverReader[0].readLine());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }
            } else if (menuResponse == 2) {
                //Search & View Users
                System.out.println("Enter Username:");
                String searchName = scanner.nextLine();
                serverWriter[0].println(searchName);
                try {
                    String message = serverReader[0].readLine();
                    if (message.isEmpty()) {
                        System.out.println("User not found");
                    } else {
                        //View User
                        System.out.println(message); //Name
                        message = serverReader[0].readLine();
                        System.out.println(message); //Description
                        message = serverReader[0].readLine();
                        System.out.println(message); //Friends

                        //Options to do on chosen User
                        System.out.println("What would you like to do to the user?" +
                            "\n1. Add or remove Friend" +
                            "\n2. Block or Unblock User" +
                            "\n3. Message" +
                            "\n4. Back");
                        int searchUserOption = Integer.parseInt(scanner.nextLine()); //Assume right input
                        serverWriter[0].println(searchUserOption);

                        switch (searchUserOption) {
                            case 1:
                                System.out.println("Would you like to add or remove a friend? (1 or 2)");
                                String ans2 = scanner.nextLine();


                                serverWriter[0].println(ans2);

                                if (ans2.equals("1")) {
                                    try {
                                        System.out.println(serverReader[0].readLine());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                                else if (ans2.equals("2")) {
                                    try {
                                        System.out.println(serverReader[0].readLine());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                break;
                            case 2:
                                System.out.println("Would you like to add or remove a Blocked user? (1 or 2)");
                                String ans3 = scanner.nextLine();
                                serverWriter[0].println(ans3);
                                if (ans3.equals("1")) {
                                    try {
                                        System.out.println(serverReader[0].readLine());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                                else if (ans3.equals("2")) {
                                    try {
                                        System.out.println(serverReader[0].readLine());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                break;
                            case 3:
                                //Message User
                                boolean canMessage = Boolean.parseBoolean(serverReader[0].readLine());
                                //Checking blocked
                                if (!canMessage) {
                                    System.out.println("Either you or your receiver is blocked");
                                    break;
                                }

                                //Loading Messages
                                String currentHistoryMessage = serverReader[0].readLine();
                                while (currentHistoryMessage != null) {
                                    System.out.println(currentHistoryMessage);
                                    currentHistoryMessage = serverReader[0].readLine();
                                }

                                //Message Loop
                                Thread messageHandler = new Thread(new MessageOutputHandler(serverReader[0]));
                                messageHandler.start();
                                while (true) {
                                    String send = scanner.nextLine();
                                    serverWriter[0].println(send);
                                    if (send.equals("/bye")) break;
                                }
                                break;
                            default: //Back
                        }
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            } else if(menuResponse==3){
                //Exit
                running= false;
            }
        }
    }

    private static void createServerStreams(Socket server, BufferedReader[] serverReader, PrintWriter[] serverWriter) {
        try {
            serverReader[0] = new BufferedReader(new InputStreamReader(server.getInputStream()));
            serverWriter[0] = new PrintWriter(server.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static Socket createServer() {
        try {
            Socket server = new Socket("localhost", 8080);
            return server;
        } catch (Exception e) {
            System.out.println("Failed to connect to the server!");
            return null;
        }
    }
    
    private static void closeServer(Socket server) {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}