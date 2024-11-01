import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class UserTest {





    @Test
    void UserUnequalstest() {
        User user1 = new User("a", "12345", new Profile("a","blank", new ArrayList<String>(null), new ArrayList<String>(null)));
        User user2 = new User("b", "12345", new Profile("b","blank", new ArrayList<String>(null), new ArrayList<String>(null)));
        assertFalse(user1.equals(user2));
    }

    @Test
    void UserEqualstest() {
        User user1 = new User("a", "12345", new Profile("a","blank", new ArrayList<String>(null), new ArrayList<String>(null)));
        User user2 = new User("a", "12345", new Profile("a","blank", new ArrayList<String>(null), new ArrayList<String>(null)));
        assertTrue(user1.equals(user2));
    }

}