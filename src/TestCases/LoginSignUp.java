import java.util.HashMap;
import java.util.Map;

public class LoginSignUp {
    private Map<String, String> userDatabase; // Stores userID and password pairs

    public LoginSignUp() {
        userDatabase = new HashMap<>();
    }

    /**
     * Sign up a new user.
     * @param userId - The user ID for the new user.
     * @param password - The password for the new user.
     * @throws IllegalArgumentException if the password is less than 6 characters.
     */
    public void signUp(String userId, String password) {
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        userDatabase.put(userId, password);
        System.out.println("User created!");
    }

    /**
     * Log in a user.
     * @param userId - The user ID for the user trying to log in.
     * @param password - The password for the user trying to log in.
     * @return true if login is successful, false otherwise.
     */
    public boolean login(String userId, String password) {
        if (userDatabase.containsKey(userId) && userDatabase.get(userId).equals(password)) {
            System.out.println("Welcome " + userId + "!");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if a user is registered.
     * @param userId - The user ID to check.
     * @return true if the user is registered, false otherwise.
     */
    public boolean isUserRegistered(String userId) {
        return userDatabase.containsKey(userId);
    }
}