import javax.swing.*;
import java.util.ArrayList;

/**
 * The ChatMenu interface. Storing all the methods used in the ChatMenu class.
 * <p>
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Le
 * @version December 7, 2024
 */

public interface ChatMenuInterface {
    public void addMessage(String messageText, boolean includeTimestamp);
    public String getChatMessage();
    public void removeRow(int index);
    public void setClientLoginUsername(String loginUsername);
}
