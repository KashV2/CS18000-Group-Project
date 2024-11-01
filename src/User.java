import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private String loginUsername;
    private String password;
    private Profile profile;

    public User(String loginUsername, String password, Profile profile) {
        this.loginUsername = loginUsername;
        this.password = password;
        this.profile = profile;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public String getPassword() {
        return password;
    }

    public Profile getProfile() {
        return profile;
    }

    public boolean equalsUsername(User user) {
        if(user.getLoginUsername().equals(loginUsername)) {
            return true;
        }
        return false;
    }

    public boolean equalsPassword(User user) {
        if(user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}