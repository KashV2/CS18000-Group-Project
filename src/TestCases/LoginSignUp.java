import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginSignUp {
    private Map<String, String> userDatabase; // Stores userID and password pairs in memory
    private static final String FILE_NAME = "user_credentials.txt";

    public LoginSignUp() {
        userDatabase = new HashMap<>();
        loadUserCredentials();
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
        saveUserCredentials();
        System.out.println("User created!");
    }

    /**
     * Log in a user.
     * @param userId - The user ID for the user trying to log in.
     * @param password - The password for the user trying to log in.
     * @return true if login is successful, false otherwise.
     */
    public boolean login(String userId, String password) {
        return userDatabase.containsKey(userId) && userDatabase.get(userId).equals(password);
    }

    /**
     * Check if a user is registered.
     * @param userId - The user ID to check.
     * @return true if the user is registered, false otherwise.
     */
    public boolean isUserRegistered(String userId) {
        return userDatabase.containsKey(userId);
    }

    /**
     * Load user credentials from a file.
     */
    private void loadUserCredentials() {
        userDatabase.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String userId = parts[0];
                    String password = parts[1];
                    userDatabase.put(userId, password);
                }
            }
        } catch (IOException e) {
            System.out.println("No existing user credentials found, starting fresh.");
        }
    }

    /**
     * Save user credentials to a file.
     */
    private void saveUserCredentials() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, String> entry : userDatabase.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error saving user credentials: " + e.getMessage());
        }
    }

    /**
     * Clear all stored user credentials from file and memory.
     */
    public void clearUserCredentials() {
        userDatabase.clear();
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.print("");
        } catch (IOException e) {
            System.out.println("Error clearing user credentials: " + e.getMessage());
        }
    }
}
