import javax.swing.*;
import java.util.ArrayList;

public interface ChatMenuInterface {
    public void addMessage(String messageText, boolean includeTimestamp);
    public String getChatMessage();
    public void removeRow(int index);
    public void setClientLoginUsername(String loginUsername);
}
