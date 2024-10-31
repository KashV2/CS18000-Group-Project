import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket server = createServer();
        if (server == null) return;
        System.out.println("Connected to Server!");

        Scanner scanner = new Scanner(System.in);

        //Validate input
        int input = 0;
        while (true) {
            System.out.println("Do you want to sign in or create a new account?\n1 Sign in\n2 Create new account");
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input != 1 && input != 2) {
                    throw new RuntimeException();
                }
                break;
            } catch (RuntimeException e) {
                closeServer(server);
                System.out.println("Invalid input!");
            }
        }

        System.out.println("Please enter your username: ");
        String loginUsername = scanner.nextLine();

        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();
        switch (input) {
            case 1:
                //Send a login request to the sever
                break;
            case 2:
                //Send a create new account request to the server;
                break;
            default:
        }
    }
    
    private static Socket createServer() {
        try {
            Socket server = new Socket("10.24.176.212", 9000);
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