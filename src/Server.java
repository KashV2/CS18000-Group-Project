import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
    private static ArrayList<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {
        ServerSocket serverSocket = createServerSocket();
        if (serverSocket == null) return;

        while (true) {
            Socket client = createClient(serverSocket);
            if (client == null) {
                closeServerSocket(serverSocket);
                return;
            }
            clients.add(client);
            Thread clientHandler = new Thread(new ClientHandler(client));
            clientHandler.start();
        }
    }

    private static ServerSocket createServerSocket() {
        try {
            ServerSocket ss = new ServerSocket(9000);
            return ss;
        } catch (IOException e) {
            System.out.println("Could not create server socket");
            return null;
        }
    }

    private static void closeServerSocket(ServerSocket ss) {
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Socket createClient(ServerSocket serverSocket) {
        try {
            Socket client = serverSocket.accept();
            return client;
        } catch (IOException e) {
            System.out.println("Could not create client");
            return null;
        }
    }
}