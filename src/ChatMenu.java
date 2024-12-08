import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The ChatMenu class. This is the menu that appears when we are conversing with another user.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Gia Le
 * @version December 7, 2024
 */

public class ChatMenu extends JFrame implements ActionListener, ChatMenuInterface {
    private ArrayList<JPanel> messageRows = new ArrayList<>();
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
        textField.addActionListener(e -> sendButton.doClick()); // Send message on pressing Enter
        inputPanel.add(textField);
        inputPanel.add(sendButton);

        // Back button panel (Top-right corner)
        JPanel topPanel = new JPanel(new BorderLayout());
        backButton.addActionListener(this);
        topPanel.add(backButton, BorderLayout.EAST);

        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Add padding around components
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Display the frame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            message = textField.getText();
            if (!message.isEmpty()) {
                addMessage(clientLoginUsername + ": " + message, true);
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

    public void addMessage(String messageText, boolean includeTimestamp) {
        JPanel messageRow = new JPanel(new BorderLayout());
        String timestamp = new SimpleDateFormat("HH:mm").format(new Date());
        JLabel messageLabel = new JLabel((includeTimestamp ? "[" + timestamp + "] " : "") + messageText);
        JButton deleteButton = new JButton("X");
        deleteButton.setForeground(Color.RED);
        deleteButton.setPreferredSize(new Dimension(45, 30));
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setContentAreaFilled(false);

        // Action to delete the message row
        deleteButton.addActionListener(e -> {
            try {
                messageQueue.put("/" + messageRows.indexOf(messageRow));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        messageRow.add(messageLabel, BorderLayout.CENTER);
        messageRow.add(deleteButton, BorderLayout.EAST);
        messagePanel.add(messageRow);
        messageRows.add(messageRow);
        messagePanel.revalidate();
        messagePanel.repaint();

        // Auto-scroll to the bottom
        JScrollBar vertical = ((JScrollPane) messagePanel.getParent().getParent()).getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    public String getChatMessage() {
        String currentMessage = message;
        message = null;
        return currentMessage;
    }

    public void removeRow(int index) {
        messagePanel.remove(messageRows.get(index));
        messageRows.remove(index);
        messagePanel.revalidate();
        messagePanel.repaint();
    }

    public void setClientLoginUsername(String loginUsername) {
        clientLoginUsername = loginUsername;
    }

    public static void main(String[] args) {
        new ChatMenu(new LinkedBlockingQueue<>());
    }
}
