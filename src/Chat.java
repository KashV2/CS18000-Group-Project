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
        this.id = loginUsername1.compareTo(loginUsername2) < 0 ? loginUsername1 : loginUsername2;
    }

    public String getId() {
        return id;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
