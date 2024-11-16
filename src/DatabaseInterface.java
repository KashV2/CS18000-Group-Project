import java.util.ArrayList;
/**
 * The Database interface. Responsible for storing all the methods used in the Database class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Kashyap Vallur
 * @version November 3, 2024
 */
public interface DatabaseInterface {
    public void addUser(User user);
    public ArrayList<User> getUsers();
    public boolean nameAlreadyExists(String username);
    public boolean passwordAlreadyExists(String password);
    public User getUser(String username);
    public String friendUser(String username1, String username2);
    public String blockUser(String username1, String username2);
}