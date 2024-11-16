import java.io.Serializable;
import java.util.*;

/**
 * The User class. Representing a user of the social media app, which
 * stores a username, password, and profile
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Abhinav Kotamreddy
 * @author Kashyap Vallur
 * @version November 3, 2024
 */

public class User implements Serializable, UserInterface {
    private String loginUsername;
    private String password;
    private Profile profile;

    //user constructor
    public User(String loginUsername, String password, Profile profile) {
        this.loginUsername = loginUsername;
        this.password = password;
        this.profile = profile;
    }

    //username getter
    public String getLoginUsername() {
        return loginUsername;
    }

    //password getter
    public String getPassword() {
        return password;
    }

    //profile getter
    public Profile getProfile() {
        return profile;
    }

    //checks if object has that username
    public boolean equalsUsername(String username) {
        return username.equals(loginUsername);
    }

    //checks if object has that password
    public boolean equalsPassword(String comparePassword) {
        return this.password.equals(comparePassword);
    }
}
