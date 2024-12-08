import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

/**
 * The NameDescChangeMenu class. This is the menu that appears when we are changing either our name or description
 * <p>
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Gia Le
 * @version December 7, 2024
 */

public class NameDescChangeMenu extends JFrame implements ActionListener, NameDescChangeMenuInterface {
    private final JButton sendButton = new JButton("Change");
    private final JLabel label = new JLabel();
    private final JTextField textField = new JTextField();
    private final CountDownLatch latch;
    private String message;

    public NameDescChangeMenu(CountDownLatch latch, String message) {
        this.latch = latch;
        this.message = message;

        setTitle("Change Name or Description");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        label.setText(message);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        textField.setPreferredSize(new Dimension(400, 40));
        textField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JPanel inputPanel = new JPanel();
        inputPanel.add(textField);
        add(inputPanel, BorderLayout.CENTER);

        sendButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        sendButton.setForeground(Color.BLACK);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        sendButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            message = textField.getText();
            latch.countDown();
        }
        dispose();
    }

    public String getMessage() {
        return message;
    }

    
}
