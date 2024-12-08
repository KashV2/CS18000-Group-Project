import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.lang.Thread;

/**
 * The Server program. Run this on one computer once before starting up any client application.
 * This program allows clients to properly connect to the server in a thread-safe way.
 * <p>
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @version November 17, 2024
 */

public class Server implements Runnable, ServerInterface {
    private final static Database DB = new Database();
    private final static ChatDatabase CHAT_DB = new ChatDatabase();
    private static ArrayList<Socket> clients = new ArrayList<>();
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        Thread serverThread = new Thread(new Server());
        //Only one server so call run
        serverThread.run();
    }

    public static void removeClient(ClientHandler sender, Socket client) {
        clientHandlers.remove(sender);
        clients.remove(client);
    }

    public static ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public static Database getDatabase() {
        return DB;
    }

    public static ChatDatabase getChatDatabase() {
        return CHAT_DB;
    }

    public void run() {
        //Create Server Socket
        serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            //Create client
            Socket client = null;
            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                //Close All Sockets
                try {
                    if (serverSocket != null) serverSocket.close();
                    for (Socket onlineClient : clients) {
                        if (onlineClient != null) onlineClient.close();
                    }
                } finally {
                    return;
                }
            }

            clients.add(client);
            ClientHandler clientHandler = new ClientHandler(client);
            clientHandlers.add(clientHandler);
            Thread clientHandlerThread = new Thread(clientHandler);
            clientHandlerThread.start();
        }
    }
}