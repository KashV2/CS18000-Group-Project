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
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveChat(Chat chat) {
        try (FileInputStream fis = new FileInputStream("chats.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            FileOutputStream fos = new FileOutputStream("chats.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            Chat readChat = (Chat)ois.readObject();
            while (readChat != null) {
                if (!chat.equals(readChat)) {
                    //Rewrite chats that is not supposed to be replaced
                    oos.writeObject(readChat);
                    oos.flush();
                } else {
                    //Replace correct chat
                    oos.writeObject(chat);
                    oos.flush();
                    break;
                }
                readChat = (Chat)ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to save chat");
            e.printStackTrace();
        }
    }

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

    public boolean chatRegistered(Chat chat) {
        for(int i = 0; i < chats.size(); i++) {
            if(chats.get(i).equals(chat)) {
                return true;
            }
        }
        return false;
    }
}
