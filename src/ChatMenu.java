import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;


public class ChatMenu extends JFrame implements ActionListener {
    JButton sendButton = new JButton("Send");
    JTextField textField = new JTextField();
    JTextArea textArea = new JTextArea();
    String message;
    private final BlockingQueue<String> messageQueue;


    public <Jbutton> ChatMenu(BlockingQueue<String> messageQueue) {
        this.messageQueue = messageQueue;
        // Set up the frame
        setTitle("Chatting with");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setVisible(false);



        // Setup button and textfield
        textArea.setEditable(false);
        sendButton.setPreferredSize(new Dimension(100, 30));
        sendButton.addActionListener((ActionListener) this);
        textField.setPreferredSize(new Dimension(600, 30));
        textArea.setPreferredSize(new Dimension(700, 400));

        // Wrap the JTextArea in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(textField, BorderLayout.SOUTH);
        this.add(sendButton);

        // Display the frame
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            message = textField.getText();
            textArea.setText(textArea.getText() + message + "\n" );
            textField.setText("");
            try {
                messageQueue.put(message); // Add message to the queue
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addMessage(String message) {
        SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
    }

    public String getChatMessage() {
        String currentMessage = message;
        message = null;
        return currentMessage;
    }


    public static void main(String[] args) {


    }
}
