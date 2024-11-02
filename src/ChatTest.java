import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {
    void testChatNotEquals() {
        // Test equals method for Chat class when not equal
        Chat chat1 = new Chat("bob", "joe");
        Chat chat2 = new Chat("bob", "joseph");
        assertFalse(chat1.equals(chat2));
    }
    @Test
    void testChatEquals() {
        // Test equals method for Chat class when  equal
        Chat chat1 = new Chat("bob", "joe");
        Chat chat2 = new Chat("joe", "bob");
        assertTrue(chat1.equals(chat2));
    }
    @Test
    void testAddMessage() {
        // test working addmessage function
        Chat chat1 = new Chat("man", "woman");
        chat1.addMessage("message");
        assertTrue(chat1.getMessages().contains("message"));
    }
    @Test
    void testAddMessageFail() {
        // test failing addmessage function
        Chat chat1 = new Chat("man", "woman");
        chat1.addMessage("message");
        assertEquals(1, chat1.getMessages().size());
    }
}

