import javax.swing.*;
import java.awt.*;
/**
 * The MainMenu interface. Storing all the methods used in the MainMenu class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Le
 * @version December 7, 2024
 */
public interface MainMenuInterface {
    public JButton createStyledButton(JButton button);
    public int getMenuResponse();
}