import java.io.*;
import java.util.ArrayList;

public class ChatDatabase implements ChatDatabasable {
    private ArrayList<Chat> chats = new ArrayList<>();

    public ChatDatabase() {
        try (FileInputStream fileIn = new FileInputStream("chats.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Chat chat = (Chat) in.readObject();
            while (chat != null) {
                chats.add(chat);
                chat = (Chat)in.readObject();
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
            Chat readChat = (Chat)ois.readObject();
            while (readChat != null) {
                if (!chat.equals(readChat)) {
                    //Rewrite chats that is not supposed to be replaced
                    newChats.add(readChat);
                } else {
                    //Replace correct chat
                    newChats.add(chat);
                }
                readChat = (Chat)ois.readObject();
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
        try (FileOutputStream fileOut = new FileOutputStream("chats.dat", true);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
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
        for(int i = 0; i < chats.size(); i++) {
            if(chats.get(i).equals(chat)) {
                return true;
            }
        }
        return false;
    }
}
