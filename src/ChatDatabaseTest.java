import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ChatDatabaseTest class. Testing the functionality of the ChatDatabase class
 * <p>
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @author Rong Yang
 * @version November 3, 2024
 */

class ChatDatabaseTest {

    @Test
    void Databasetest1() {
        ChatDatabase dbtest = new ChatDatabase();
        Chat chat1 = new Chat("user1", "user2");
        Chat chat2 = new Chat("user1", "user2");
        chat1.addMessage("meow");
        chat2.addMessage("meow");
        chat1.addMessage("ahhhhh");
        chat2.addMessage("ahhhhh");
        dbtest.addChat(chat1);
        assertTrue(dbtest.chatRegistered(chat2));
    }

    @Test
    public void Databasetest2() {
        // Set up a stream to capture System.out output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            ChatDatabase dbtest = new ChatDatabase();
            Chat chat1 = new Chat("user1", "user2");
            Chat chat2 = new Chat("user3", "user4");

            chat1.addMessage("meow");
            chat2.addMessage("bark");
            chat2.addMessage("ahhhhhhh");
            chat1.addMessage("mwahahahaha");

            dbtest.addChat(chat1);
            dbtest.addChat(chat2);
            String output = outputStream.toString().trim();
            output = output.replaceAll("\r", ""); // Remove carriage return characters for cross-platform compatibility


            assertEquals(output, "Chat has been registered!\nChat has been registered!");

        } finally {
            System.setOut(originalOut);
        }
    }
}