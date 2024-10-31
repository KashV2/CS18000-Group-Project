import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class LoginSignUpClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server");

            while (true) {
                System.out.println("User’s Menu:");
                System.out.println("1. Login");
                System.out.println("2. Sign up");
                System.out.print("Choose an option (1 or 2): ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                System.out.print("Enter your userID: ");
                String userId = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                if (choice == 1) {
                    // Send login request
                    out.println("login " + userId + " " + password);
                } else if (choice == 2) {
                    // Send sign up request
                    out.println("signup " + userId + " " + password);
                } else {
                    System.out.println("Invalid option. Try again.");
                    continue;
                }

                // Read server response
                String response = in.readLine();
                System.out.println(response);
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
