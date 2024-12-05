import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatMenu extends JFrame implements ActionListener {
    private String clientLoginUsername;
    JButton sendButton = new JButton("Send");
    JButton backButton = new JButton("Back");
    JTextField textField = new JTextField();
    JPanel messagePanel = new JPanel(); // Panel for messages with delete buttons
    String message;
    private final BlockingQueue<String> messageQueue;

    public ChatMenu(BlockingQueue<String> messageQueue) {
        this.messageQueue = messageQueue;

        // Set up the frame
        setTitle("Chatting with");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set up the message panel
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(messagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Setup input field and buttons
        JPanel inputPanel = new JPanel(new FlowLayout());
        textField.setPreferredSize(new Dimension(600, 30));
        sendButton.setPreferredSize(new Dimension(100, 30));
        sendButton.addActionListener(this);
        backButton.addActionListener(this);
        inputPanel.add(textField);
        inputPanel.add(sendButton);
        inputPanel.add(backButton);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Display the frame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            message = textField.getText();
            if (!message.isEmpty()) {
                addMessage(clientLoginUsername + ": " + message);
                textField.setText("");
                try {
                    messageQueue.put(message); // Add message to the queue
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (e.getSource() == backButton) {
            try {
                messageQueue.put("/bye"); // Add message to the queue
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addMessage(String messageText) {
        JPanel messageRow = new JPanel(new BorderLayout());
        JLabel messageLabel = new JLabel(messageText);
        JButton deleteButton = new JButton("X");
        deleteButton.setForeground(Color.RED);
        deleteButton.setPreferredSize(new Dimension(45, 30));
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setContentAreaFilled(false);

        // Action to delete the message row
        deleteButton.addActionListener(e -> {
            messagePanel.remove(messageRow);
            messagePanel.revalidate();
            messagePanel.repaint();
        });

        messageRow.add(messageLabel, BorderLayout.CENTER);
        messageRow.add(deleteButton, BorderLayout.EAST);
        messagePanel.add(messageRow);
        messagePanel.revalidate();
        messagePanel.repaint();
    }

    public String getChatMessage() {
        String currentMessage = message;
        message = null;
        return currentMessage;
    }

    public void setClientLoginUsername(String loginUsername) {
        clientLoginUsername = loginUsername;
    }

    public static void main(String[] args) {
        new ChatMenu(new LinkedBlockingQueue<>());
    }
}
