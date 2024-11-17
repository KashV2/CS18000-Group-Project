import java.util.ArrayList;
/**
 * The Profile interface. Responsible for storing all the methods used in the Profile class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Kashyap Vallur
 * @version November 3, 2024
 */
public interface ProfileInterface {
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
    public boolean isBlocked(User user);
    public boolean isFriend(Profile profile);
    public boolean isFriend(User user);
    public void addBlock(String username);
}