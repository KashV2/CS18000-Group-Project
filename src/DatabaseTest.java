import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database database;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        // Initialize Database and Users with Profile instances
        database = new Database();

        ArrayList<String> friendsList1 = new ArrayList<>();
        ArrayList<String> blockedUsersList1 = new ArrayList<>();
        Profile profile1 = new Profile("User One", "Description 1", friendsList1, blockedUsersList1);

        ArrayList<String> friendsList2 = new ArrayList<>();
        ArrayList<String> blockedUsersList2 = new ArrayList<>();
        Profile profile2 = new Profile("User Two", "Description 2", friendsList2, blockedUsersList2);

        user1 = new User("user1", "password1", profile1);
        user2 = new User("user2", "password2", profile2);
    }

    @AfterEach
    void tearDown() {
        // Clean up the file after each test to avoid test contamination
        File file = new File("users.dat");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testAddUser() {
        // Add a user and verify it is added to the list
        database.addUser(user1);
        ArrayList<User> users = database.getUsers();
        assertEquals(1, users.size());
        assertEquals(user1, users.get(0));
    }

    @Test
    void testGetUser() {
        // Add users and check retrieval by username
        database.addUser(user1);
        database.addUser(user2);
        assertEquals(user1, database.getUser("user1"));
        assertEquals(user2, database.getUser("user2"));
        assertNull(database.getUser("nonExistentUser"));
    }

    @Test
    void testNameAlreadyExists() {
        database.addUser(user1);
        assertTrue(database.nameAlreadyExists(user1), "Username should already exist.");
        assertFalse(database.nameAlreadyExists(user2), "Username should not exist yet.");
    }

    @Test
    void testPasswordAlreadyExists() {
        database.addUser(user1);
        assertTrue(database.passwordAlreadyExists(user1), "Password should already exist.");
        assertFalse(database.passwordAlreadyExists(user2), "Password should not exist yet.");
    }

    @Test
    void testFriendUser() {
        // Setting up initial friend relationships
        database.addUser(user1);
        database.addUser(user2);
        user1.getProfile().getFriends().remove("user2"); // Ensure they are not friends initially

        String result = database.friendUser("user1", "user2");
        assertEquals("Friended Successfully!", result, "Users should be friended successfully.");
        assertTrue(user1.getProfile().getFriends().contains("user2"), "User1 should have user2 as a friend.");
        assertTrue(user2.getProfile().getFriends().contains("user1"), "User2 should have user1 as a friend.");
    }

    @Test
    void testBlockUser() {
        // Setting up initial block relationship
        database.addUser(user1);
        database.addUser(user2);
        user1.getProfile().getFriends().remove("user2"); // Ensure they are not friends initially

        String result = database.blockUser("user1", "user2");
        assertEquals("User Blocked", result, "User2 should be blocked by user1.");
        assertTrue(user1.getProfile().getBlockedUsers().contains("user2"), "User2 should be blocked by user1.");
        assertFalse(user2.getProfile().getFriends().contains("user1"), "User2 should not be a friend of user1.");
    }

    @Test
    void testBlockAndUnfriendUser() {
        // Add users and make them friends initially
        database.addUser(user1);
        database.addUser(user2);
        user1.getProfile().getFriends().add("user2");
        user2.getProfile().getFriends().add("user1");

        // Block user2 from user1's side
        String result = database.blockUser("user1", "user2");
        assertEquals("User Blocked and Unfriended", result, "User2 should be unfriended and blocked by user1.");
        assertTrue(user1.getProfile().getBlockedUsers().contains("user2"), "User2 should be blocked by user1.");
        assertFalse(user1.getProfile().getFriends().contains("user2"), "User2 should no longer be a friend of user1.");
    }
}
