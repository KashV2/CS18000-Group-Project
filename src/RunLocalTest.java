import org.junit.jupiter.api.*;
import java.net.Socket;
import java.util.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

/**
 * Project's test.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @version November 16, 2024
 */
public class RunLocalTest {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ChatDatabaseTest {
/**
 * The tests.ChatDatabaseTest program. Responsible for validating the ChatDatabase class
 * and ensuring that data is persistent
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @author Rong Yang
 * @version November 3, 2024
 */
        @Test
        void databaseTest1() {
            ChatDatabase dbTest = new ChatDatabase();
            Chat chat1 = new Chat("user1", "user2");
            dbTest.addChat(chat1);
            chat1.addMessage("meow");
            chat1.addMessage("ahhhhh");
            dbTest.saveChat(chat1);

            ChatDatabase dbTest2 = new ChatDatabase();
            Chat chat2 = new Chat("user2", "user1");
            Assertions.assertTrue(dbTest2.chatRegistered(chat2));
            ArrayList<String> chatMessages = dbTest2.getChats().get(0).getMessages();
            assertEquals("meow", chatMessages.get(0));
            assertEquals("ahhhhh", chatMessages.get(1));
        }

        @AfterEach
        void tearDown() {
            File file = new File("chats.dat");
            if (file.exists()) file.delete();
        }
    }

    @Nested
    class ChatTest {
/**
 * The ChatTest class. Responsible for validating the Chat class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Abhinav Kotamreddy
 * @version November 3, 2024
 */
        @Test
        void testChatNotEquals() {
            Chat chat1 = new Chat("bob", "joe");
            Chat chat2 = new Chat("bob", "joseph");
            assertFalse(chat1.equals(chat2));
        }

        @Test
        void testChatEquals() {
            Chat chat1 = new Chat("bob", "joe");
            Chat chat2 = new Chat("joe", "bob");
            assertTrue(chat1.equals(chat2));
        }

        @Test
        void testAddMessage() {
            Chat chat1 = new Chat("man", "woman");
            chat1.addMessage("message");
            assertTrue(chat1.getMessages().contains("message"));
        }

        @Test
        void testRemoveMessage() {
            Chat chat1 = new Chat("man", "woman");
            chat1.addMessage("message");
            chat1.removeMessage(0);
            assertEquals(0, chat1.getMessages().size());
        }
    }

    @Nested
    class ClientTest {
        private Method createServerMethod;
        private Method createServerStreamsMethod;
        private Method closeServerMethod;

        @BeforeEach
        void setUp() throws Exception {
            createServerMethod = Client.class.getDeclaredMethod("createServer");
            createServerMethod.setAccessible(true);

            createServerStreamsMethod = Client.class.getDeclaredMethod("createServerStreams", Socket.class, BufferedReader[].class, PrintWriter[].class);
            createServerStreamsMethod.setAccessible(true);

            closeServerMethod = Client.class.getDeclaredMethod("closeServer", Socket.class);
            closeServerMethod.setAccessible(true);
        }
        // @Test
        // void testCreateServer_Success() {
        //     try {
        //         Socket serverSocket = (Socket) createServerMethod.invoke(null);
        //         assertNotNull(serverSocket, "Server socket should not be null.");
        //         if (serverSocket != null) {
        //             serverSocket.close(); // Clean up the socket if it was successfully created
        //         }
        //     } catch (Exception e) {
        //         fail("Exception occurred during createServer execution: " + e.getMessage());
        //     }
        // }
        // @Test
        // void testCreateServerStreams_Success() {
        //     try {
        //         Socket serverSocket = new StubSocket();
        //         BufferedReader[] serverReader = new BufferedReader[1];
        //         PrintWriter[] serverWriter = new PrintWriter[1];

        //         createServerStreamsMethod.invoke(null, serverSocket, serverReader, serverWriter);

        //         assertNotNull(serverReader[0], "BufferedReader should have been initialized.");
        //         assertNotNull(serverWriter[0], "PrintWriter should have been initialized.");
        //     } catch (Exception e) {
        //         fail("Exception occurred during createServerStreams execution: " + e.getMessage());
        //     }
        // }

        @Test
        void testCloseServer_Success() {
            try {
                Socket serverSocket = new StubSocket();
                closeServerMethod.invoke(null, serverSocket);
                assertTrue(serverSocket.isClosed(), "Socket should be closed after invoking closeServer.");
            } catch (Exception e) {
                fail("Exception occurred during closeServer execution: " + e.getMessage());
            }
        }

        @Test
        void testUserLoginInput() {
            // Simulate user input for sign-in
            InputStream testInput = new ByteArrayInputStream("1\nusername\npassword\n".getBytes());
            System.setIn(testInput);
    
            assertDoesNotThrow(() -> Client.main(new String[0]), "Client execution failed with simulated user input.");
        }

        private static class StubSocket extends Socket {
            private boolean isClosed = false;

            @Override
            public synchronized void close() {
                isClosed = true;
            }

            @Override
            public boolean isClosed() {
                return isClosed;
            }
        }
    }

    @Nested
    class DatabaseTest {
/**
 * The DatabaseTest class. Responsible for validating the Database class
 * and ensuring User data is persistent
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Bach Gia Le
 * @version November 3, 2024
 */
        private Database database;
        private User user1;
        private User user2;

        @BeforeEach
        void setUp() {
            // Initialize Database and Users with Profile instances
            database = new Database();

            ArrayList<String> friendsList1 = new ArrayList<>();
            ArrayList<String> blockedUsersList1 = new ArrayList<>();
            Profile profile1 = new Profile("User One", "Description 1",
                friendsList1, blockedUsersList1);

            ArrayList<String> friendsList2 = new ArrayList<>();
            ArrayList<String> blockedUsersList2 = new ArrayList<>();
            Profile profile2 = new Profile("User Two", "Description 2",
                friendsList2, blockedUsersList2);

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

            //Ensure that our database is actually saving the data and can be retrieved at start
            Database secondDatabase = new Database();
            ArrayList<User> secondUsers = secondDatabase.getUsers();
            assertEquals(1, secondUsers.size());
            assertEquals(user1.getLoginUsername(), secondUsers.get(0).getLoginUsername());
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
            assertTrue(database.nameAlreadyExists(user1.getLoginUsername()),
                "Username should already exist.");
            assertFalse(database.nameAlreadyExists(user2.getLoginUsername()),
                "Username should not exist yet.");
        }

        @Test
        void testPasswordAlreadyExists() {
            database.addUser(user1);
            assertTrue(database.passwordAlreadyExists(user1.getPassword()),
                "Password should already exist.");
            assertFalse(database.passwordAlreadyExists(user2.getPassword()),
                "Password should not exist yet.");
        }

        @Test
        void testFriendUser() {
            // Setting up initial friend relationships
            database.addUser(user1);
            database.addUser(user2);
            user1.getProfile().getFriends().remove("user2"); // Ensure they are not friends initially

            String result = database.friendUser("user1", "user2");
            assertEquals("Friended Successfully!", result,
                "Users should be friended successfully.");
            assertTrue(user1.getProfile().getFriends().contains("user2"),
                "User1 should have user2 as a friend.");
            assertTrue(user2.getProfile().getFriends().contains("user1"),
                "User2 should have user1 as a friend.");
        }

        @Test
        void testBlockUser() {
            // Setting up initial block relationship
            database.addUser(user1);
            database.addUser(user2);
            user1.getProfile().getFriends().remove("user2"); // Ensure they are not friends initially

            String result = database.blockUser("user1", "user2");
            assertEquals("User Blocked", result,
                "User2 should be blocked by user1.");
            assertTrue(user1.getProfile().getBlockedUsers().contains("user2"),
                "User2 should be blocked by user1.");
            assertFalse(user2.getProfile().getFriends().contains("user1"),
                "User2 should not be a friend of user1.");
        }

        @Test
        void testBlockAndUnfriendUser() {
            // Add users to the database
            database.addUser(user1);
            database.addUser(user2);

            // Use the friendUser function to make user1 and user2 friends
            String friendshipResult = database.friendUser("user1", "user2");
            assertEquals("Friended Successfully!", friendshipResult,
                "User1 and User2 should be friends initially.");

            // Verify that user1 and user2 are now friends
            assertTrue(user1.getProfile().getFriends().contains("user2"),
                "User1 should have User2 as a friend.");
            assertTrue(user2.getProfile().getFriends().contains("user1"),
                "User2 should have User1 as a friend.");

            // Block user2 from user1's side and verify the outcome
            String blockResult = database.blockUser("user1", "user2");
            assertEquals("User Blocked and Unfriended", blockResult,
                "User2 should be unfriended and blocked by User1.");

            // Verify that user2 is now blocked by user1
            assertTrue(user1.getProfile().getBlockedUsers().contains("user2"),
                "User2 should be blocked by User1.");
            assertFalse(user1.getProfile().getFriends().contains("user2"),
                "User2 should no longer be a friend of User1.");
            assertFalse(user2.getProfile().getFriends().contains("user1"),
                "User1 should no longer be a friend of User2.");
        }
    }


    @Nested
    class ProfileTest {
/**
 * The ProfileTest program. Responsible for validating the Profile class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Bach Gia Le
 * @author Rong Yang
 * @version November 3, 2024
 */
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

    @Nested
    class UserTest {
/**
 * The UserTest class. Responsible for validating the User class.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Bach Gia Le
 * @author Rong Yang
 * @version November 3, 2024
 */
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

    public static void main(String[] args) {
        org.junit.platform.console.ConsoleLauncher.main(new String[]{"--select-class", "RunLocalTest"});
    }
}
