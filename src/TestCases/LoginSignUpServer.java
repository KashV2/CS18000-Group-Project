import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginSignUpServer {
    private static final int PORT = 12345;
    private LoginSignUp loginSignUp;

    public LoginSignUpServer() {
        loginSignUp = new LoginSignUp();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                // Create a new thread for each client
                new ClientHandlerTest(clientSocket, loginSignUp).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        LoginSignUpServer server = new LoginSignUpServer();
        server.start();
    }
}

class ClientHandlerTest extends Thread {
    private Socket clientSocket;
    private LoginSignUp loginSignUp;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandlerTest(Socket socket, LoginSignUp loginSignUp) {
        this.clientSocket = socket;
        this.loginSignUp = loginSignUp;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String command;
            while ((command = in.readLine()) != null) {
                String[] parts = command.split(" ");
                String action = parts[0];

                if ("signup".equalsIgnoreCase(action)) {
                    String userId = parts[1];
                    String password = parts[2];
                    try {
                        loginSignUp.signUp(userId, password);
                        out.println("User created successfully!");
                    } catch (IllegalArgumentException e) {
                        out.println(e.getMessage());
                    }
                } else if ("login".equalsIgnoreCase(action)) {
                    String userId = parts[1];
                    String password = parts[2];
                    if (loginSignUp.login(userId, password)) {
                        out.println("Welcome " + userId + "!");
                    } else {
                        out.println("Invalid credentials");
                    }
                } else {
                    out.println("Unknown command");
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
