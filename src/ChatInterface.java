import java.util.ArrayList;

public interface ChatInterface {
    public String getId();
    public ArrayList<String> getMessages();
    public String getMessage(int index);
    public void addMessage(String message);
    public void removeMessage(int index);
}