import java.util.ArrayList;

public interface ChatDatabasable {
    public ArrayList<Chat> getChats();
    public void saveChat(Chat chat);
    public void addChat(Chat chat);
    public boolean chatRegistered(Chat chat);
}
