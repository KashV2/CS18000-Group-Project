import java.awt.*;
import javax.swing.*;
/**
 * The SignInMenu interface. Storing all the methods used in the SignInMenu class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Le
 * @version December 7, 2024
 */
public interface SignInMenuInterface {
    public JButton createGradientButton(String text, Color startColor, Color endColor);
    public void setSignInButtonVisible(boolean visible);
    public int getOutput();
}