import java.io.IOException;
import java.util.ArrayList;
/**
 * The ChatDatabase interface. Storing all the methods used in the ChatDatabase class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @version November 3, 2024
 */
public interface ChatDatabaseInterface {
    public ArrayList<Chat> getChats();
    void saveChats() throws IOException;
    public void addChat(Chat chat);
    public boolean chatRegistered(Chat chat);
}