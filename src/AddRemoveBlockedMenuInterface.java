import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * The AddRemoveBlockedMenu interface. Storing all the methods used in the AddRemoveBlockedMenu class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Gia Le
 * @version December 7, 2024
 */

public interface AddRemoveBlockedMenuInterface {

    void styleButton(JButton button, Color backgroundColor);

    void actionPerformed(ActionEvent e);

    int getMenuResponse();
}


