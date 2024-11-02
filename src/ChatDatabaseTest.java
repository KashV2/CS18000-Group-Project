import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatDatabaseTest {

    @Test
    void Databasetest1() {
        ChatDatabase dbtest = new ChatDatabase();
        Chat chat1 = new Chat("user1","user2");
        dbtest.addChat(chat1);
        chat1.addMessage("meow");
        chat1.addMessage("ahhhhh");
        dbtest.saveChat(chat1);

        ChatDatabase dbtest2 = new ChatDatabase();
        ArrayList<String> chatMessages = dbtest2.getChats().get(0).getMessages();
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