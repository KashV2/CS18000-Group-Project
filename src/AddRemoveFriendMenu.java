import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

/**
 * The AddRemoveFriendMenu class. This is the menu that appears when we are trying to add or remove a user as a friend.
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Gia Le
 * @version December 7, 2024
 */

public class AddRemoveFriendMenu extends JFrame implements ActionListener, AddRemoveFriendMenuInterface {
    private final JButton removeButton = new JButton("Remove Friend");
    private final JButton addButton = new JButton("Add Friend");
    private final JLabel instructionLabel = new JLabel("Would you like to add or remove a friend?");
    private int menuResponse;
    private final CountDownLatch latch;

    public AddRemoveFriendMenu(CountDownLatch latch) {
        this.latch = latch;

        // Frame setup
        setTitle("Friend Management");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // Instruction Panel
        JPanel topPanel = new JPanel();
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(instructionLabel);
        add(topPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        styleButton(addButton, new Color(20, 128, 20));
        styleButton(removeButton, new Color(220, 20, 60));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void styleButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            menuResponse = 1;
        } else if (e.getSource() == removeButton) {
            menuResponse = 2;
        }
        latch.countDown();
        dispose();
    }

    public int getMenuResponse() {
        return menuResponse;
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> new AddRemoveFriendMenu(latch));
    }
}
