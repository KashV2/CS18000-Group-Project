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

    public void removeFriend(String username) throws UserNotFoundException {
        try {
            this.friends.remove(username);
        } catch (Exception e) {
            throw new UserNotFoundException("User does not exist");
        }
    }

    public void addFriend(String username) throws UserNotFoundException {
        try {
            this.friends.add(username);
        } catch (Exception e) {
            throw new UserNotFoundException("User does not exist");
        }
    }

    public void removeBlock(String username) throws UserNotFoundException {
        try {
            this.blockedUsers.remove(username);
        } catch (Exception e) {
            throw new UserNotFoundException("User does not exist");
        }
    }

    public void addBlock(String username) UserNotFoundException {
        try {
            this.blockedUsers.add(username);
        } catch (Exception e) {
            throw new UserNotFoundException("User does not exist");
        }
    }