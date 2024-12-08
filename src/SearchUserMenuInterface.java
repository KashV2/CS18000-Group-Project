import javax.swing.*;
import java.awt.*;
/**
 * The SearchUserMenu interface. Storing all the methods used in the SearchUserMenu class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Le
 * @version December 7, 2024
 */
public interface SearchUserMenuInterface {
    public void styleButton(JButton button, Color backgroundColor);
    public void triggerBarrier();
    public void displayUserNotFound();
    public boolean isBackPressed();
    public void userActionMenu(String userInfo);
    public String getSearchedUser();
    public int getMenuResponse();
}