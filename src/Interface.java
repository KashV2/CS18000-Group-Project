import java.util.ArrayList;
public class Interface {

public interface ProfileInterface {
/**
 * The Profile interface. Responsible for storing all the methods used in the Profile class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Kashyap Vallur
 * @version November 3, 2024
 */
    public String getName();
    public String getDescription();
    public ArrayList<String> getFriends();
    public ArrayList<String> getBlockedUsers();
    public void setName(String name);
    public void setDescription(String description);
    public void removeFriend(String username);
    public void addFriend(String username);
    public void removeBlock(String username);
    public boolean isBlocked(Profile profile);
    public boolean isFriend(Profile profile);
    public void addBlock(String username);
}

public interface UserInterface {
/**
 * The User interface. Responsible for storing all the methods used in the User class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Kashyap Vallur
 * @version November 3, 2024
 */
    public String getLoginUsername();
    public String getPassword();
    public Profile getProfile();
    public boolean equalsUsername(String username);
    public boolean equalsPassword(String password);
}
    
public interface DatabaseInterface {
/**
 * The Database interface. Responsible for storing all the methods used in the Database class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Kashyap Vallur
 * @version November 3, 2024
 */
    public void addUser(User user);
    public ArrayList<User> getUsers();
    public boolean nameAlreadyExists(String username);
    public boolean passwordAlreadyExists(String password);
    public User getUser(String username);
    public String friendUser(String username1, String username2);
    public String blockUser(String username1, String username2);
}

public interface ChatInterface {
/**
 * The Chat interface. Responsible for storing all the methods used in the Chat class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @version November 3, 2024
 */
    public String getId();
    public ArrayList<String> getMessages();
    public String getMessage(int index);
    public void addMessage(String message);
    public void removeMessage(int index);
}

public interface ChatDatabaseInterface {
/**
 * The ChatDatabase interface. Storing all the methods used in the ChatDatabase class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @version November 3, 2024
 */
    public ArrayList<Chat> getChats();
    public void saveChats();
    public void addChat(Chat chat);
    public boolean chatRegistered(Chat chat);
}
public interface AppendingObjectOutputStreamInterface {
}

}
