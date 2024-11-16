import java.util.ArrayList;
/**
 * The Chat interface. Responsible for storing all the methods used in the Chat class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @version November 3, 2024
 */
public interface ChatInterface {
    public String getId();
    public ArrayList<String> getMessages();
    public String getMessage(int index);
    public void addMessage(String message);
    public void removeMessage(int index);
}