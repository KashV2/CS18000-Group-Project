import java.io.*;
import java.util.ArrayList;

/**
 * The Database class. Responsible for storing all the registered users.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Kashyap Vallur
 * @version November 3, 2024
 */

public class Database {
    private ArrayList<User> users;

    //reads in all users from file
    public Database() {
        users = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream("users.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            User user = (User) in.readObject();
            while (user != null) { // Continue until an EOFException is caught
                users.add(user);
                user = (User) in.readObject();
            }
        } catch (FileNotFoundException | EOFException | StreamCorruptedException e) {
            //Ignore
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // add user to arraylist and output to the file
    public void addUser(User user) {

        try (FileOutputStream fileOut = new FileOutputStream("users.dat", true);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            users.add(user);
            out.writeObject(user);
            out.flush();
            System.out.println("User data has been appended to 'users'.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //getter
    public ArrayList<User> getUsers() {
        return users;
    }

    // checks if name already exists, after server makes a user to add, it will check if it already exists
    public boolean nameAlreadyExists(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equalsUsername(username)) {
                return true;
            }
        }
        return false;
    }

    //checks if password is already used
    public boolean passwordAlreadyExists(String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equalsPassword(password)) {
                return true;
            }
        }
        return false;
    }


    // gets and returns user object with that username
    public User getUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLoginUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    //attempts to friend user and gives a string message based off how it goes
    public String friendUser(String username1, String username2) {

        User user1 = getUser(username1);
        User user2 = getUser(username2);

        if (user1.getProfile().isFriend(user2.getProfile()) ||
            user2.getProfile().isFriend(user1.getProfile())) {
            return " Already Friended";
        } else if (user1.getProfile().isBlocked(user2.getProfile())) {
            return "First user blocked";
        } else if (user2.getProfile().isBlocked(user1.getProfile())) {
            return "Second user blocked";
        } else {
            user1.getProfile().addFriend(username2);
            user2.getProfile().addFriend(username1);
            return "Friended Successfully!";
        }
    }

    //attempts to friend user and gives a string message based off how it goes
    public String blockUser(String username1, String username2) {
        User user1 = getUser(username1);
        User user2 = getUser(username2);

        if (user1.getProfile().isBlocked(user2.getProfile()) ||
            user2.getProfile().isBlocked(user1.getProfile())) {
            return " Already Blocked";
        } else if (user1.getProfile().getFriends().contains("user2")
            && user2.getProfile().getFriends().contains("user1")) {
            user1.getProfile().removeFriend(username2);
            user2.getProfile().removeFriend(username1);
            user1.getProfile().addBlock(username2);
            return "User Blocked and Unfriended";
        } else {
            user1.getProfile().addBlock(username2);
            return "User Blocked";
        }
    }
}