import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void SetDescriptiontest() {
        Profile testProfile = new Profile("a","generic description", new ArrayList<String>(null), new ArrayList<String>(null));
        testProfile.setDescription("test");
        assertEquals("test", testProfile.getDescription());
    }

    @Test
    void setNametest() {
        Profile testProfile = new Profile("a","generic description", new ArrayList<String>(null), new ArrayList<String>(null));
        testProfile.setName("test");
        assertEquals("test", testProfile.getName());
    }

    @Test
    void removeFriendtest() {
        Profile testProfile = new Profile("a", "generic description", new ArrayList<String>(Arrays.asList("b","c","d")), new ArrayList<String>(null));
        testProfile.removeFriend("b");
        ArrayList<String> expectedFriends = new ArrayList<String>(Arrays.asList("c","d"));
        assertEquals(expectedFriends, testProfile.getFriends());

    }

    @Test
    void addFriendtest() {
        Profile testProfile = new Profile("a", "generic description", new ArrayList<String>(Arrays.asList("b","c","d")), new ArrayList<String>(null));
        testProfile.addFriend("e");
        ArrayList<String> expectedFriends = new ArrayList<>(Arrays.asList("b","c","d","e"));
        assertEquals(expectedFriends, testProfile.getFriends());
    }

    @Test
    void removeBlocktest() {
        Profile testProfile = new Profile("a", "generic description", new ArrayList<String>(null), new ArrayList<String>(Arrays.asList("b","c","d")));
        testProfile.removeBlock("b");
        ArrayList<String> expectedBlock = new ArrayList<>(Arrays.asList("c","d"));
        assertEquals(expectedBlock, testProfile.getBlockedUsers());
    }

    @Test
    void addBlocktest() {
        Profile testProfile = new Profile("a", "generic description", new ArrayList<String>(null), new ArrayList<String>(Arrays.asList("b","c","d")));
        testProfile.addBlock("e");
        ArrayList<String> expectedBlock = new ArrayList<>(Arrays.asList("b","c","d","e"));
        assertEquals(expectedBlock, testProfile.getBlockedUsers());
    }

    @Test
    void isFriendtest() {
        Profile testProfile = new Profile("a", "generic description", new ArrayList<String>(Arrays.asList("b","c","d")), new ArrayList<String>(null));
        assertTrue(testProfile.isFriend("b"));
    }

    @Test
    void isBlockedtest() {
        Profile testProfile = new Profile("a", "generic description", new ArrayList<String>(null), new ArrayList<String>(Arrays.asList("b","c","d")));
        assertTrue(testProfile.isBlocked("b"));
    }


}