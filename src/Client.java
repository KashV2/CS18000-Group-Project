import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    private static String loginUsername = null;
    private static String password = null;

    public static void main(String[] args) {
        Socket server = createServer();
        if (server == null) return;
        System.out.println("Connected to Server!");

        Scanner scanner = new Scanner(System.in);
        BufferedReader[] serverReader = new BufferedReader[1];
        PrintWriter[] serverWriter = new PrintWriter[1];
        createServerStreams(server, serverReader, serverWriter);

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
        loginUsername = scanner.nextLine();

        System.out.println("Please enter your password: ");
        password = scanner.nextLine();

        //Send login or create new account request to server (must be in this order)
        serverWriter[0].println(loginUsername);
        serverWriter[0].println(password);
        serverWriter[0].println(signInResponse);

        //AFTER LOGGING IN -- Main Menu / Loop
        //Choose between editing self or searching & viewing other profile
        while (true) {
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

            //Handle Menu Selection
            if (menuResponse == 1) {
                //Edit User Profile
            } else if (menuResponse == 2) {
                //Search & View Users
            } else {
                //Exit
                break;
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