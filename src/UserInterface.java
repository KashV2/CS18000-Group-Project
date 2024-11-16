/**
 * The User interface. Responsible for storing all the methods used in the User class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Kashyap Vallur
 * @version November 3, 2024
 */
public interface UserInterface {
    public String getLoginUsername();
    public String getPassword();
    public Profile getProfile();
    public boolean equalsUsername(String username);
    public boolean equalsPassword(String password);
}