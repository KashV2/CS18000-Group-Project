import java.util.Scanner;
import java.net.*;
import java.io.*;

public class MessageOutputHandler implements Runnable {
    private BufferedReader reader;

    public MessageOutputHandler(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String incomingMessage = reader.readLine();
                if (incomingMessage.equals("/bye")) break;
                System.out.println("\n" + incomingMessage);
                //Messaging will be a little funky when both are typing and sending at the same time
                //Because if one sends it'll kind of be printed next to what the other is trying to type
                //But this will be cleared up in the GUI phase where we have our dedicated spaces to type
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}