import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader clientReader;
    private PrintWriter clientWriter;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            clientWriter = new PrintWriter(client.getOutputStream(), true);

            String loginUsername = clientReader.readLine();
            String password = clientReader.readLine();
            int signInResponse = Integer.parseInt(clientReader.readLine());
            boolean signedIn = false;
            while (!signedIn) {
                switch (signInResponse) {
                case 1:
                    //Sign in request to database
                    //Check correct username and password
                    //On fail send information to the client of how we failed
                    //If we could sign in, send information back to the client that we successfully signed in
                    break;
                case 2:
                    //Create new account request to database
                    //Check if the username already exists but same password as another user is okay
                    //On fail send information to the client of how we failed
                    //If we could create an account, create it on the database, sign in on the client, and send information back to the client on the successful creation
                    break;
                default:
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
