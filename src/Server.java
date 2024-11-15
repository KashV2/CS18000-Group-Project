import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.lang.Thread;

public class Server implements Runnable{
    private static ArrayList<Socket> clients = new ArrayList<>();
    private static Database db = new Database();

    public static void main(String[] args) {
        Thread serverThread = new Thread(new Server());
        //Only one server so call run
        serverThread.run();
    }

    public void run() {
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

    public static Database getDatabase() {
        return db;
    }

    private static ServerSocket createServerSocket() {
        try {
            ServerSocket ss = new ServerSocket(8080);
            return ss;
        } catch (IOException e) {
            return null;
        }
    }

    private static void closeServerSocket(ServerSocket ss) {
        try {
            if (ss != null) ss.close();
            for (Socket client : clients) {
                if (client != null) client.close();
            }
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