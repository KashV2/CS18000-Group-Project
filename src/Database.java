import java.io.FileInputStream;
import java.io.ObjectInputStream;

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

    public addUser(User user) {

        try (FileOutputStream fileOut = new FileOutputStream("users", true);
             ObjectOutputStream out = new AppendableObjectOutputStream("users.dat")) {
            users.add(user);
            out.write(user);
            System.out.println("User data has been appended to 'users'.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public user[] getUsers() {
        return users;
    }

    public boolean alreadyExists(User user) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).equals(user)) {
                return true;
            }
        }
        return false;

    }


}