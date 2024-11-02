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

    public boolean equalsUsername(String username) {
        if(username.equals(loginUsername)) {
            return true;
        }
        return false;
    }

    public boolean equalsPassword(String password) {
        if(this.password.equals(password)) {
            return true;
        }
        return false;
    }
}