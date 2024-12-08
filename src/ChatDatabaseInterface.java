import java.io.IOException;
import java.util.ArrayList;

/**
 * The ChatDatabase interface. Storing all the methods used in the ChatDatabase class.
 * <p>
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @version November 3, 2024
 */
public interface ChatDatabaseInterface {
    public Chat getChat(Chat chat);

    public ArrayList<Chat> getChats();

    public void saveChats();

    public void addChat(Chat chat);

    public boolean chatRegistered(Chat chat);
}