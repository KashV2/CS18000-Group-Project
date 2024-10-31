import java.util.*;

public class User implements UserInterface {
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
}