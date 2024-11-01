import java.util.*;
public class Profile extends User {
    private String name;
    private String description;
    private ArrayList<String> friends;
    private ArrayList<String> blockedUsers;

    public Profile(String name, String description, ArrayList<String> friends, ArrayList<String> blockedUsers) {
        this.name = name;
        this.description = description;
        this.friends = friends;
        this.blockedUsers = blockedUsers;
    }

    public String getName() {

        return name;

    }

    public String getDescription() {

        return description;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Future work: check for user existence throw exception if user does not exist
    public void removeFriend(String username) {
        this.friends.remove(username);
    }

    // Future work: check for user existence throw exception if user does not exist
    public void addFriend(String username) {
        this.friends.add(username);
    }

    // Future work: check for user existence throw exception if user does not exist
    public void removeBlock(String username) {
        if (this.blockedUsers.contains(username)) {
            this.blockedUsers.remove(username);
        }
    }

    public boolean isBlocked(String username) {
        return this.blockedUsers.contains(username);
    }

    public boolean isFriend(String username) {
        return this.friends.contains(username);
    }



    // Future work: check for user existence throw exception if user does not exist
    public void addBlock(String username) {
        this.blockedUsers.add(username);
    }