import java.io.Serializable;
import java.util.*;

/**
 * The Profile class. Representing a user's profile that others can see.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Abhinav Kotamreddy
 * @author Kashyap Vallur
 * @version November 3, 2024
 */

public class Profile implements Serializable, Interface.ProfileInterface {
    private String name;
    private String description;
    private ArrayList<String> friends;
    private ArrayList<String> blockedUsers;

    // profile constructor
    public Profile(String name, String description,
            ArrayList<String> friends, ArrayList<String> blockedUsers) {
        this.name = name;
        this.description = description;
        this.friends = friends;
        this.blockedUsers = blockedUsers;
    }

    // name getter
    public String getName() {
        return name;
    }

    //description getter
    public String getDescription() {

        return description;
    }

    //friends getter
    public ArrayList<String> getFriends() {
        return friends;
    }

    //returns all blocked users
    public ArrayList<String> getBlockedUsers() {
        return blockedUsers;
    }

    //name setter
    public void setName(String name) {
        this.name = name;
    }

    //description setter
    public void setDescription(String description) {
        this.description = description;
    }

    //remove friend from arraylist
    // Future work: check for user existence throw exception if user does not exist
    public void removeFriend(String username) {
        this.friends.remove(username);

    }

    //add friend to arraylist
    // Future work: check for user existence throw exception if user does not exist
    public void addFriend(String username) {
        this.friends.add(username);
    }

    //remove user from blocked list
    // Future work: check for user existence throw exception if user does not exist
    public void removeBlock(String username) {
        if (this.blockedUsers.contains(username)) {
            this.blockedUsers.remove(username);
        }
    }

    //checks if user is blocked
    public boolean isBlocked(Profile profile) {
        return this.blockedUsers.contains(profile.getName());
    }

    //checks if user is friend
    public boolean isFriend(Profile profile) {
        return this.friends.contains(profile.getName());
    }


    //add user to blocked list
    // Future work: check for user existence throw exception if user does not exist
    public void addBlock(String username) {
        this.blockedUsers.add(username);
    }
}
