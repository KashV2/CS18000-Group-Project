import com.sun.tools.javac.Main;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * The Client program. Run this to connect to a server and interact with the application.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @author Abhinav Kotamreddy
 * @author Kashyap Vallur
 * @version November 17, 2024
 */

public class Client implements Runnable, ClientInterface {
    public static void main(String[] args) {
        Thread clientThread = new Thread(new Client());
        clientThread.run(); //Only one
    }

    public void run() {
        //Connect to server
        Socket server = null;
        try {
            server = new Socket("localhost", 8080);
        } catch (Exception e) {
            System.out.println("Failed to connect to the server!");
            return;
        }

        System.out.println("Connected to Server!");
        Scanner scanner = new Scanner(System.in);
        BufferedReader[] serverReader = new BufferedReader[1]; //Use index zero for access
        PrintWriter[] serverWriter = new PrintWriter[1]; //Use index zero for access
        //Create Server Streams
        try {
            serverReader[0] = new BufferedReader(new InputStreamReader(server.getInputStream()));
            serverWriter[0] = new PrintWriter(server.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String loginUsername = null;
        boolean signedIn = false;
        while (!signedIn) {
            //Validate signInResponse
            int signInResponse = 0;
            while (true) {
                CountDownLatch latch = new CountDownLatch(1);
                SignInMenu menu1 = new SignInMenu(latch);
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (menu1.getOutput() != 1 && menu1.getOutput() != 2) {
                        throw new RuntimeException();
                    }
                    signInResponse = menu1.getOutput();
                    break;
                } catch (RuntimeException e) {
                    System.out.println("Invalid input!");
                }
            }

            //Login input
            CountDownLatch latch = new CountDownLatch(1);
            LogInMenu menu2 = new LogInMenu(latch);
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loginUsername = menu2.getUsername();
            String password = menu2.getPassword();

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
                        JOptionPane.showMessageDialog(null, errorMessage, "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //AFTER LOGGING IN -- Main Menu / Loop
        //Choose between editing self or searching & viewing other profile
        boolean running = true;
        boolean continueInSearch = false;
        boolean openSearch = true;
        CyclicBarrier barrier = null;
        SearchUserMenu menu5 = null;
        while (running) {
            int menuResponse = 0;
            if (continueInSearch == false) {
                while (true) {
                    CountDownLatch latch = new CountDownLatch(1);
                    MainMenu menu3 = new MainMenu(latch);
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        menuResponse = menu3.getMenuResponse();
                        if (menuResponse < 1 || menuResponse > 3) throw new RuntimeException();
                        break;
                    } catch (RuntimeException e) {
                        System.out.println("Invalid input!");
                    }
                }
            } else {
                menuResponse = 2;
            }

            //Send Menu Selection to Server
            serverWriter[0].println(menuResponse);

            //Handle Menu Selection
            if (menuResponse == 1) {
                //Edit User Profile
                //Retrieve info about yourself
                String nameLabel = "";
                String descriptionLabel = "";
                String friendsLabel = "";
                String blockedLabel = "";
                try {
                    nameLabel = serverReader[0].readLine(); //Name
                    descriptionLabel = serverReader[0].readLine(); //Description
                    friendsLabel = serverReader[0].readLine(); //Friends
                    blockedLabel = serverReader[0].readLine(); //Blocked
                } catch (IOException e) {
                    e.printStackTrace();
                }

                CountDownLatch latch = new CountDownLatch(1);
                ChangeNameDescriptionMenu menu4 = new ChangeNameDescriptionMenu(latch);
                menu4.initialize(nameLabel, descriptionLabel, friendsLabel, blockedLabel);
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int ans = menu4.getMenuResponse();
                serverWriter[0].println(ans);

                switch (ans) {
                case 1:
                    CountDownLatch latch2 = new CountDownLatch(1);
                    NameDescChangeMenu menu9 = new NameDescChangeMenu(latch2, "Please enter your new name");
                    try {
                        latch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String newName = menu9.getMessage();
                    serverWriter[0].println(newName);
                    try {
                        System.out.println(serverReader[0].readLine());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    JOptionPane.showMessageDialog(
                        null,"Succesfully Changed Name!","Changing Profile", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2:
                    CountDownLatch latch3 = new CountDownLatch(1);
                    NameDescChangeMenu menu10 = new NameDescChangeMenu(latch3, "Please enter your new description");
                    try {
                        latch3.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String newDescription = menu10.getMessage();
                    serverWriter[0].println(newDescription);
                    try {
                        System.out.println(serverReader[0].readLine());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    JOptionPane.showMessageDialog(
                        null,"Succesfully Changed Description!","Changing Profile", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            } else if (menuResponse == 2) {
                //Search & View Users
                if (!continueInSearch || openSearch) {
                    barrier = new CyclicBarrier(2);
                    menu5 = new SearchUserMenu(barrier);
                } else {
                    continueInSearch = false;
                    openSearch = true;
                }
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                boolean re_search = false;
                do {
                    re_search = false;
                    String searchName = menu5.getSearchedUser();
                    serverWriter[0].println(searchName);
                    try {
                        String message = serverReader[0].readLine();
                        if (message.isEmpty() && !menu5.isBackPressed()) {
                            JOptionPane.showMessageDialog(
                                null,"Username does not exist","Input Error", JOptionPane.ERROR_MESSAGE);
                            menu5.displayUserNotFound();
                            continueInSearch = true;
                            openSearch = false;
                        } else if (!menu5.isBackPressed()) {
                            //View User
                            String userDescription;
                            userDescription = message + "\n";//Name
                            message = serverReader[0].readLine();
                            userDescription += message +"\n";//Description
                            message = serverReader[0].readLine();
                            userDescription += message + "\n";//Friends


                            //Options to do on chosen User
                            menu5.userActionMenu(userDescription);
                            try {
                                barrier.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (BrokenBarrierException e) {
                                throw new RuntimeException(e);
                            }
                            menu5.dispose();
                            int searchUserOption = menu5.getMenuResponse(); //Assume right input
                            serverWriter[0].println(searchUserOption);

                            switch (searchUserOption) {
                            case 1:
                                CountDownLatch latch = new CountDownLatch(1);
                                AddRemoveFriendMenu menu6 = new AddRemoveFriendMenu(latch);
                                try {
                                    latch.await();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                String ans2 = String.valueOf(menu6.getMenuResponse());


                                serverWriter[0].println(ans2);

                                if (ans2.equals("1")) {
                                    try {
                                        String temp = serverReader[0].readLine();

                                        JOptionPane.showMessageDialog(null,temp,"Changing Friend Status", JOptionPane.INFORMATION_MESSAGE);
                                        continueInSearch = false;
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                } else if (ans2.equals("2")) {
                                    try {

                                        String temp = serverReader[0].readLine();

                                        JOptionPane.showMessageDialog(null,temp,"Changing Friend Status", JOptionPane.INFORMATION_MESSAGE);
                                        continueInSearch = false;
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                break;
                            case 2:
                                CountDownLatch latch2 = new CountDownLatch(1);
                                AddRemoveBlockedMenu menu7 = new AddRemoveBlockedMenu(latch2);
                                try {
                                    latch2.await();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                String ans3 = String.valueOf(menu7.getMenuResponse());
                                serverWriter[0].println(ans3);
                                if (ans3.equals("1")) {
                                    try {
                                        String temp = serverReader[0].readLine();

                                        JOptionPane.showMessageDialog(null,temp,"Changing Block Status", JOptionPane.INFORMATION_MESSAGE);
                                        continueInSearch = false;
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                } else if (ans3.equals("2")) {
                                    try {
                                        String temp = serverReader[0].readLine();

                                        JOptionPane.showMessageDialog(null,temp,"Changing Block Status", JOptionPane.INFORMATION_MESSAGE);
                                        continueInSearch = false;
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
                                    JOptionPane.showMessageDialog(
                                        null,"Either you or your receiver is blocked","Messaging", JOptionPane.ERROR_MESSAGE);
                                    continueInSearch = true;
                                    break;
                                }

                                BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
                                ChatMenu chatMenu = new ChatMenu(messageQueue);
                                chatMenu.setClientLoginUsername(loginUsername);

                                //Loading Messages
                                String currentHistoryMessage = serverReader[0].readLine();
                                while (!currentHistoryMessage.equals("<~!||NULL||!~>")) {
                                    chatMenu.addMessage(currentHistoryMessage, true); // false if no timestamp
                                    currentHistoryMessage = serverReader[0].readLine();
                                }

                                //Message Loop
                                Thread messageHandler = new Thread(new MessageOutputHandler(serverReader[0], chatMenu));
                                messageHandler.start();
                                while (true) {
                                    String send = messageQueue.take();
                                    serverWriter[0].println(send);
                                    if (send.equals("/bye")) {
                                        chatMenu.dispose();
                                        break;
                                    }
                                    try {
                                        Thread.sleep(100); // Add a small delay to avoid busy waiting
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                try {
                                    messageHandler.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            default: //Back
                                if (menu5.isBackPressed()) {
                                    serverWriter[0].println("");
                                    menu5.dispose();
                                    break;
                                }
                                re_search = true;
                                serverWriter[0].println("Re-search");
                            }
                        } else {
                            menu5.dispose();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } while (re_search);
            } else if (menuResponse == 3) {
                //Exit
                running = false;
            }
        }

        //Close Server
        try {
            if (server != null) server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}