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

    public synchronized ArrayList<Chat> getChats() {
        return chats;
    }

    //Step 3
    //When sending messages, after a client sends something, we should update the chat in the db
    public synchronized void saveChats() {
        //Actually rewriting all chats into the database
        try (FileOutputStream fos = new FileOutputStream("chats.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Chat newChat : chats) {
                oos.writeObject(newChat);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Step 2
    //In the server, if the chat is not registered, we will add it to the db
    public synchronized void addChat(Chat chat) {
        chats.add(chat);
        System.out.println("Chat has been registered!");

        saveChats();
    }

    //Step 1
    //Check to see if a chat between these two people already exist in the db
    public synchronized boolean chatRegistered(Chat chat) {
        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i).equals(chat)) {
                return true;
            }
        }
        return false;
    }
}
