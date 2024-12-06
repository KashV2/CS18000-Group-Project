import java.util.Scanner;
import java.net.*;
import java.io.*;

/**
 * The MessageOutputHandler program. A thread that listens to incoming messages from the other person during
 * direct messaging so that you can also send messages to them at the same time.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Jason Chan
 * @version November 17, 2024
 */

public class MessageOutputHandler implements Runnable, MessageOutputHandlerInterface {
    private BufferedReader reader;
    private ChatMenu chatMenu;

    public MessageOutputHandler(BufferedReader reader, ChatMenu chatMenu) {
        this.reader = reader;
        this.chatMenu = chatMenu;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String incomingMessage = reader.readLine();
                if (incomingMessage.equals("/bye")) break;
                if (incomingMessage.charAt(0) == '/') {
                    if (incomingMessage.length() == 1) continue;
                    chatMenu.removeRow(Integer.parseInt(incomingMessage.substring(1)));
                    continue;
                }
                System.out.print(incomingMessage);
                chatMenu.addMessage(incomingMessage, true);
                //Messaging will be a little funky when both are typing and sending at the same time
                //Because if one sends it'll kind of be printed next to what the other is trying to type
                //But this will be cleared up in the GUI phase where we have our dedicated spaces to type
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}