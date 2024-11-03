import java.util.ArrayList;

public interface DatabaseInterface {
    public void addUser(User user);
    public ArrayList<User> getUsers();
    public boolean nameAlreadyExists(String username);
    public boolean passwordAlreadyExists(String password);
    public User getUser(String username);
    public String friendUser(String username1, String username2);
    public String blockUser(String username1, String username2);
}
