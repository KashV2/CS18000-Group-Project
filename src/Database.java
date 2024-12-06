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

public class Database implements DatabaseInterface {
    private ArrayList<User> users;

    //reads in all users from file
    public Database() {
        users = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream("users.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            while (true) { // Continue until an EOFException is caught
                User user = (User) in.readObject();
                System.out.println("Login Name: " + user.getLoginUsername() + " " +
                    "Profile Name: " + user.getProfile().getName() + " " +
                    "Description: " + user.getProfile().getDescription());
                users.add(user);
            }
        } catch (FileNotFoundException | EOFException | StreamCorruptedException e) {
            //Ignore
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // add user to arraylist and output to the file
    public synchronized void addUser(User user) {
        users.add(user);
        System.out.println(user.getProfile().getName() + " " + user.getProfile().getDescription());
        System.out.println("User data has been appended to 'users'.");

        saveUsers();
    }

    //getter
    public synchronized ArrayList<User> getUsers() {
        return users;
    }

    // checks if name already exists, after server makes a user to add, it will check if it already exists
    public synchronized boolean nameAlreadyExists(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equalsUsername(username)) {
                return true;
            }
        }
        return false;
    }

    //checks if password is already used
    public synchronized boolean passwordAlreadyExists(String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equalsPassword(password)) {
                return true;
            }
        }
        return false;
    }


    // gets and returns user object with that username
    public synchronized User getUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLoginUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    //attempts to friend user and gives a string message based off how it goes
    public synchronized String friendUser(String username1, String username2) {

        User user1 = getUser(username1);
        User user2 = getUser(username2);

        if (user1.getProfile().isFriend(user2) ||
            user2.getProfile().isFriend(user1)) {
            return "Already Friended";
        } else if (user1.getProfile().isBlocked(user2)) {
            return "First user blocked";
        } else if (user2.getProfile().isBlocked(user1)) {
            return "Second user blocked";
        } else {
            user1.getProfile().addFriend(username2);
            if (!user1.getLoginUsername().equals(user2.getLoginUsername()))
                user2.getProfile().addFriend(username1);
            return "Friended Successfully!";
        }
    }

    //attempts to friend user and gives a string message based off how it goes
    public synchronized String blockUser(String username1, String username2) {
        User user1 = getUser(username1);
        User user2 = getUser(username2);

        if (user1.getProfile().isBlocked(user2) ||
            user2.getProfile().isBlocked(user1)) {
            return "Already Blocked";
        } else if (user1.getProfile().isFriend(user2)
            && user2.getProfile().isFriend(user1)) {
            user1.getProfile().removeFriend(username2);
            user2.getProfile().removeFriend(username1);
            user1.getProfile().addBlock(username2);
            return "User Blocked and Unfriended";
        } else {
            user1.getProfile().addBlock(username2);
            return "User Blocked";
        }
    }

    public synchronized User getUserFromProfileName(String profileUsername) {
        for (int i = 0; i < users.size(); i++) {
            if (profileUsername.equals(users.get(i).getProfile().getName())) {
                return users.get(i);
            }
        }
        return null;
    }

    public synchronized void saveUsers() {
        //Actually rewriting all users back into the database
        try (FileOutputStream fos = new FileOutputStream("users.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (User userInstance : users) {
                oos.writeObject(userInstance);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}