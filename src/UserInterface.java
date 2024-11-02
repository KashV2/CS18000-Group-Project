public interface UserInterface {

    public String getLoginUsername();
    public String getPassword();
    public Profile getProfile();
    public boolean equalsUsername(String username);
    public boolean equalsPassword(String password);


}
