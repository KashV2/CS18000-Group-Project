import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Database {

    private ArrayList<User> users;

    public Database() {

        users = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream("users.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            while (true) { // Continue until an EOFException is caught
                User user = (User) in.readObject();
                users.add(user);
            }
        } catch (Exception e) {
            if (e instanceof java.io.EOFException) {
                // End of file reached; handle it gracefully
                System.out.println("End of file reached.");
            } else {
                e.printStackTrace();
            }
        }

    }

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

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean nameAlreadyExists(User user) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).equalsUsername(user)) {
                return true;
            }
        }
        return false;

    }

    public boolean passwordAlreadyExists(User user) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).equalsPassword(user)) {
                return true;
            }
        }
        return false;

    }



    public User getUser(String username) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getLoginUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    public String friendUser(String username1, String username2) {

        User user1 = getUser(username1);
        User user2 = getUser(username2);

        if(user1.getProfile().isFriend(user2.getProfile()) || user2.getProfile().isFriend(user1.getProfile())) {
            return " Already Friended";
        }

        else if(user1.getProfile().isBlocked(user2.getProfile())) {
            return "First user blocked";
        }

        else if(user2.getProfile().isBlocked(user1.getProfile())) {
            return "Second user blocked";
        }

        else {
            user1.getProfile().addFriend(username2);
            user2.getProfile().addFriend(username1);
            return "Friended Successfully!";
        }


    }

    public String blockUser(String username1, String username2) {
        User user1 = getUser(username1);
        User user2 = getUser(username2);

        if(user1.getProfile().isBlocked(user2.getProfile()) || user2.getProfile().isBlocked(user1.getProfile())) {
            return " Already Blocked";
        }

        else if(user1.getProfile().isFriend(user2.getProfile()) && user1.getProfile().isFriend(user2.getProfile())) {
            user1.getProfile().removeFriend(username1);
            user2.getProfile().removeFriend(username2);
            user1.getProfile().addBlock(username2);
            return "User Blocked and Unfriended";
        }
        
        else {
            user1.getProfile().addBlock(username2);
            return "User Blocked"; 
        }
    }




}
