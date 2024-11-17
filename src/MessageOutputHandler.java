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
                System.out.println(incomingMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}