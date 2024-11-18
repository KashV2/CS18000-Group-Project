import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
/**
 * The ChatDatabaseTest program. Responsible for validating the ChatDatabase class
 * and ensuring that data is persistent
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @author Rong Yang
 * @version November 3, 2024
 */
class ChatDatabaseTest {
    @Test
    void databaseTest1() {
        ChatDatabase dbTest = new ChatDatabase();
        Chat chat1 = new Chat("user1", "user2");
        dbTest.addChat(chat1);
        chat1.addMessage("meow");
        chat1.addMessage("ahhhhh");
        dbTest.saveChats();
        ChatDatabase dbTest2 = new ChatDatabase(); // Reload from saved state
        Chat chat2 = new Chat("user2", "user1");
        
        assertTrue(dbTest2.chatRegistered(chat2));
        ArrayList<String> chatMessages = dbTest2.getChats().get(0).getMessages();
        assertEquals("meow", chatMessages.get(0));
        assertEquals("ahhhhh", chatMessages.get(1));
    }
    @AfterEach
    void tearDown() {
        // Clean up the file after each test to avoid test contamination
        File file = new File("chats.dat");
        if (file.exists()) {
            file.delete();
        }
    }
}