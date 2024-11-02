import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
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
        if(username.equals(loginUsername)) {
            return true;
        }
        return false;
    }

    //checks if object has that password
    public boolean equalsPassword(String password) {
        if(this.password.equals(password)) {
            return true;
        }
        return false;
    }
}
