import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

/**
 * The ChangeNameDescriptionMenu class. This is the menu that appears to give the user a choice
 * whether they want to change their name or their description
 * <p>
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Gia Le
 * @version December 7, 2024
 */

public class ChangeNameDescriptionMenu extends JFrame implements ActionListener, ChangeNameDescriptionMenuInterface {
    private final JButton nameButton = new JButton("Change Name");
    private final JButton descButton = new JButton("Change Description");
    private final JLabel instruction = new JLabel("Choose an option");
    private final CountDownLatch latch;
    private JLabel profileNameLabel = new JLabel("Name: ");
    private JLabel profileDescriptionLabel = new JLabel("Description: ");
    private JLabel friendsLabel = new JLabel("Friends: ");
    private JLabel blockedLabel = new JLabel("Blocked: ");
    private int menuResponse;

    public ChangeNameDescriptionMenu(CountDownLatch latch) {
        this.latch = latch;

        setTitle("Change Options");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 1));

        // Instruction label at the top
        JPanel panel = new JPanel(new GridLayout(2, 1));
        instruction.setFont(new Font("SansSerif", Font.BOLD, 16));
        instruction.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel nextPanel = new JPanel();
        panel.add(instruction);
        nextPanel.add(profileNameLabel);
        nextPanel.add(profileDescriptionLabel);
        nextPanel.add(friendsLabel);
        nextPanel.add(blockedLabel);
        add(panel, BorderLayout.NORTH);
        panel.add(nextPanel, BorderLayout.CENTER);

        // Buttons in the center
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        nameButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descButton.setFont(new Font("SansSerif", Font.PLAIN, 14));

        nameButton.setBackground(new Color(70, 130, 180));
        nameButton.setForeground(Color.BLACK);
        nameButton.setFocusPainted(false);

        descButton.setBackground(new Color(70, 130, 180));
        descButton.setForeground(Color.BLACK);
        descButton.setFocusPainted(false);

        nameButton.addActionListener(this);
        descButton.addActionListener(this);

        buttonPanel.add(nameButton);
        buttonPanel.add(descButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Center the window on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        new ChangeNameDescriptionMenu(latch);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nameButton) {
            menuResponse = 1;
        } else if (e.getSource() == descButton) {
            menuResponse = 2;
        }
        latch.countDown();
        dispose();
    }

    public int getMenuResponse() {
        return menuResponse;
    }

    public void initialize(String name, String description, String friends, String blocked) {
        profileNameLabel.setText(name);
        profileDescriptionLabel.setText(description);
        friendsLabel.setText(friends);
        blockedLabel.setText(blocked);
    }
}
