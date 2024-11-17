import java.net.ServerSocket;

public interface ServerInterface {
    public Database getDatabase();
    public ChatDatabase getChatDatabase();
    public ServerSocket createServerSocket();
    public void closeServerSocket(ServerSocket ss);
    public Socket createClient(ServerSocket serverSocket);
}
