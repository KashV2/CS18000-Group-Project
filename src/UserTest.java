import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The UserTest class. Responsible for validating the User class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Bach Gia Le
 * @author Rong Yang
 * @version November 3, 2024
 */

class UserTest {

    @Test
    void notEqualsUsernameTest() {
        User user1 = new User("a", "12345", new Profile(
            "a", "blank", new ArrayList<String>(), new ArrayList<String>()));
        User user2 = new User("b", "12345", new Profile(
            "b", "blank", new ArrayList<String>(), new ArrayList<String>()));
        assertFalse(user1.equalsUsername(user2.getLoginUsername()));
    }

    @Test
    void equalsUsernameTest() {
        User user1 = new User("a", "12345", new Profile(
            "a", "blank", new ArrayList<String>(), new ArrayList<String>()));
        User user2 = new User("a", "12345", new Profile(
            "b", "blank", new ArrayList<String>(), new ArrayList<String>()));
        assertTrue(user1.equalsUsername(user2.getLoginUsername()));
    }

    @Test
    void notEqualsPasswordTest() {
        User user1 = new User("a", "12345", new Profile(
            "a", "blank", new ArrayList<String>(), new ArrayList<String>()));
        User user2 = new User("a", "123456", new Profile(
            "a", "blank", new ArrayList<String>(), new ArrayList<String>()));
        assertFalse(user1.equalsPassword(user2.getPassword()));
    }

    @Test
    void equalsPasswordTest() {
        User user1 = new User("a", "12345", new Profile(
            "a", "blank", new ArrayList<String>(), new ArrayList<String>()));
        User user2 = new User("a", "12345", new Profile(
            "a", "blank", new ArrayList<String>(), new ArrayList<String>()));
        assertTrue(user1.equalsPassword(user2.getPassword()));
    }
}
