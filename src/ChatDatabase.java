import java.io.*;
import java.util.ArrayList;

/**
 * The ChatDatabase class. Responsible for storing Chat objects persistently.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @version November 3, 2024
 */

public class ChatDatabase implements ChatDatabaseInterface {
    private ArrayList<Chat> chats = new ArrayList<>();

    public ChatDatabase() {
        try (FileInputStream fileIn = new FileInputStream("chats.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            while (true) {
                Chat chat = (Chat)in.readObject();
                chats.add(chat);
            }
        } catch (FileNotFoundException | EOFException | StreamCorruptedException e) {
            //Ignore
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    //Step 3
    //When sending messages, after a client sends something, we should update the chat in the db
    public void saveChat(Chat chat) {
        ArrayList<Chat> newChats = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("chats.dat");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                Chat readChat = (Chat)ois.readObject();
                if (!chat.equals(readChat)) {
                    //Rewrite chats that is not supposed to be replaced
                    newChats.add(readChat);
                } else {
                    //Replace correct chat
                    newChats.add(chat);
                }
            }
        } catch (EOFException | StreamCorruptedException e) {
            //Ignore
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to save chat");
            e.printStackTrace();
        }

        //Actually rewriting all chats into the database
        try (FileOutputStream fos = new FileOutputStream("chats.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Chat newChat : newChats) {
                oos.writeObject(newChat);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Step 2
    //In the server, if the chat is not registered, we will add it to the db
    public void addChat(Chat chat) {
        boolean append = new File("chats.dat").exists();

        try (FileOutputStream fileOut = new FileOutputStream("chats.dat", append);
             ObjectOutputStream out = append ? new AppendingObjectOutputStream(fileOut) :
             new ObjectOutputStream(fileOut)) {
            chats.add(chat);
            out.writeObject(chat);
            out.flush();
            System.out.println("Chat has been registered!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Step 1
    //Check to see if a chat between these two people already exist in the db
    public boolean chatRegistered(Chat chat) {
        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i).equals(chat)) {
                return true;
            }
        }
        return false;
    }
}
