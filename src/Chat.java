import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements ChatInterface, Serializable {
    private String id;
    private ArrayList<String> messages = new ArrayList<>();

    //Creating a new chat between two people
    public Chat(String loginUsername1, String loginUsername2) {
        //Assuming loginUsernames are different
        //How to get a unique id to identify this chat:
        //Mash the loginUsernames together, whichever login comes first in the alphabet is first
        this.id = loginUsername1.compareTo(loginUsername2) < 0 ?
            loginUsername1 + loginUsername2 : loginUsername2 + loginUsername1;
    }

    //Checks if two chats have the same ID
    //This is needed for each client in the chat to access the same saved chat
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Chat)) return false;
        Chat chat = (Chat)obj;
        return this.id.equals(chat.id);
    }

    //Getting id variable
    public String getId() {
        return id;
    }

    //Getting messages variable
    public ArrayList<String> getMessages() {
        return messages;
    }

    //Gets a specific String message from the message list
    //This is useful for when trying to delete a message, we have to check if the message is our own
    public String getMessage(int index) {
        return messages.get(index);
    }

    //Add a new message from the client into the list of messages
    public void addMessage(String message) {
        messages.add(message);
    }

    //Removing a specific message from the chat, for deleting feature
    public void removeMessage(int index) {
        messages.remove(index);
    }
}
