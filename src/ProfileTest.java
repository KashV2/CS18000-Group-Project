import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void setDescriptionTest() {
        Profile testProfile = new Profile("a", "generic description",
            new ArrayList<String>(), new ArrayList<String>());
        testProfile.setDescription("test");
        assertEquals("test", testProfile.getDescription());
    }

    @Test
    void setNameTest() {
        Profile testProfile = new Profile("a", "generic description",
            new ArrayList<String>(), new ArrayList<String>());
        testProfile.setName("test");
        assertEquals("test", testProfile.getName());
    }

    @Test
    void removeFriendTest() {
        Profile testProfile = new Profile("a", "generic description",
            new ArrayList<String>(Arrays.asList("b", "c", "d")), new ArrayList<String>());
        testProfile.removeFriend("b");
        ArrayList<String> expectedFriends = new ArrayList<String>(Arrays.asList("c", "d"));
        assertEquals(expectedFriends, testProfile.getFriends());

    }

    @Test
    void addFriendTest() {
        Profile testProfile = new Profile("a", "generic description",
            new ArrayList<String>(Arrays.asList("b", "c", "d")), new ArrayList<String>());
        testProfile.addFriend("e");
        ArrayList<String> expectedFriends = new ArrayList<>(Arrays.asList("b", "c", "d", "e"));
        assertEquals(expectedFriends, testProfile.getFriends());
    }

    @Test
    void removeBlockTest() {
        Profile testProfile = new Profile("a", "generic description",
            new ArrayList<String>(), new ArrayList<String>(Arrays.asList("b", "c", "d")));
        testProfile.removeBlock("b");
        ArrayList<String> expectedBlock = new ArrayList<>(Arrays.asList("c", "d"));
        assertEquals(expectedBlock, testProfile.getBlockedUsers());
    }

    @Test
    void addBlockTest() {
        Profile testProfile = new Profile("a", "generic description",
            new ArrayList<String>(), new ArrayList<String>(Arrays.asList("b", "c", "d")));
        testProfile.addBlock("e");
        ArrayList<String> expectedBlock = new ArrayList<>(Arrays.asList("b", "c", "d", "e"));
        assertEquals(expectedBlock, testProfile.getBlockedUsers());
    }

    @Test
    void isFriendTest() {
        Profile testProfile = new Profile("a", "generic description",
            new ArrayList<String>(Arrays.asList("b", "c", "d")), new ArrayList<String>());
        Profile testProfile2 = new Profile("b", "generic description",
            new ArrayList<String>(Arrays.asList("e", "f", "g")), new ArrayList<String>());
        assertTrue(testProfile.isFriend(testProfile2));
    }

    @Test
    void isBlockedTest() {
        Profile testProfile = new Profile("a", "generic description",
            new ArrayList<String>(), new ArrayList<String>(Arrays.asList("b", "c", "d")));
        Profile testProfile2 = new Profile("b", "generic description",
            new ArrayList<String>(), new ArrayList<String>(Arrays.asList("e", "f", "g")));
        assertTrue(testProfile.isBlocked(testProfile2));
    }
}