import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {
    private String id;
    private ArrayList<String> messages = new ArrayList<>();

    //Creating a new chat between two people
    public Chat(String loginUsername1, String loginUsername2) {
        //Assuming loginUsernames are different
        //How to get a unique id to identify this chat:
        //Mash the loginUsernames together, whichever login comes first in the alphabet is first
        this.id = loginUsername1.compareTo(loginUsername2) < 0 ?
                loginUsername1 + loginUsername2: loginUsername2 + loginUsername1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Chat)) return false;
        Chat chat = (Chat)obj;
        return this.id.equals(chat.id);
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
