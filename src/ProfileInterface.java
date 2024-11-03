import java.util.ArrayList;

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
    public boolean isFriend(Profile profile);
    public void addBlock(String username);
}
