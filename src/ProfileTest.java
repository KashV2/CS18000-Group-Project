import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The ProfileTest program. Responsible for validating the Profile class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Bach Gia Le
 * @author Rong Yang
 * @version November 3, 2024
 */
class ProfileTest {

    @Test
    void setDescriptionTest() {
        Profile testProfile = new Profile("a", "generic description",
                new ArrayList<>(), new ArrayList<>());
        testProfile.setDescription("test");
        assertEquals("test", testProfile.getDescription());
    }

    @Test
    void setNameTest() {
        Profile testProfile = new Profile("a", "generic description",
                new ArrayList<>(), new ArrayList<>());
        testProfile.setName("test");
        assertEquals("test", testProfile.getName());
    }

    @Test
    void removeFriendTest() {
        Profile testProfile = new Profile("a", "generic description",
                new ArrayList<>(Arrays.asList("b", "c", "d")), new ArrayList<>());
        testProfile.removeFriend("b");
        ArrayList<String> expectedFriends = new ArrayList<>(Arrays.asList("c", "d"));
        assertEquals(expectedFriends, testProfile.getFriends());
    }

    @Test
    void addFriendTest() {
        Profile testProfile = new Profile("a", "generic description",
                new ArrayList<>(Arrays.asList("b", "c", "d")), new ArrayList<>());
        testProfile.addFriend("e");
        ArrayList<String> expectedFriends = new ArrayList<>(Arrays.asList("b", "c", "d", "e"));
        assertEquals(expectedFriends, testProfile.getFriends());
    }

    @Test
    void removeBlockTest() {
        Profile testProfile = new Profile("a", "generic description",
                new ArrayList<>(), new ArrayList<>(Arrays.asList("b", "c", "d")));
        testProfile.removeBlock("b");
        ArrayList<String> expectedBlocked = new ArrayList<>(Arrays.asList("c", "d"));
        assertEquals(expectedBlocked, testProfile.getBlockedUsers());
    }

    @Test
    void addBlockTest() {
        Profile testProfile = new Profile("a", "generic description",
                new ArrayList<>(), new ArrayList<>(Arrays.asList("b", "c", "d")));
        testProfile.addBlock("e");
        ArrayList<String> expectedBlocked = new ArrayList<>(Arrays.asList("b", "c", "d", "e"));
        assertEquals(expectedBlocked, testProfile.getBlockedUsers());
    }

    @Test
    void isFriendTestWithProfile() {
        Profile testProfile1 = new Profile("a", "generic description",
                new ArrayList<>(Arrays.asList("b", "c", "d")), new ArrayList<>());
        Profile testProfile2 = new Profile("b", "generic description",
                new ArrayList<>(Arrays.asList("e", "f", "g")), new ArrayList<>());
        assertTrue(testProfile1.isFriend(testProfile2));
    }

    @Test
    void isFriendTestWithUser() {
        Profile testProfile = new Profile("a", "generic description",
                new ArrayList<>(Arrays.asList("b", "c", "d")), new ArrayList<>());
        User testUser = new User("b", "password", new Profile("b", "desc", new ArrayList<>(), new ArrayList<>()));
        assertTrue(testProfile.isFriend(testUser));
    }

    @Test
    void isBlockedTestWithProfile() {
        Profile testProfile1 = new Profile("a", "generic description",
                new ArrayList<>(), new ArrayList<>(Arrays.asList("b", "c", "d")));
        Profile testProfile2 = new Profile("b", "generic description",
                new ArrayList<>(), new ArrayList<>(Arrays.asList("e", "f", "g")));
        assertTrue(testProfile1.isBlocked(testProfile2));
    }

    @Test
    void isBlockedTestWithUser() {
        Profile testProfile = new Profile("a", "generic description",
                new ArrayList<>(), new ArrayList<>(Arrays.asList("b", "c", "d")));
        User testUser = new User("b", "password", new Profile("b", "desc", new ArrayList<>(), new ArrayList<>()));
        assertTrue(testProfile.isBlocked(testUser));
    }
}
