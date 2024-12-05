import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class AddRemoveBlockedMenu extends JFrame implements ActionListener {
    private final JButton removeButton = new JButton("Remove Blocked");
    private final JButton addButton = new JButton("Add Blocked");
    private final JLabel instructionLabel = new JLabel("Would you like to add or remove a blocked user?");
    private int menuResponse;
    private final CountDownLatch latch;

    public AddRemoveBlockedMenu(CountDownLatch latch) {
        this.latch = latch;

        // Frame setup
        setTitle("Blocked Users");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // Instruction panel
        JPanel topPanel = new JPanel();
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(instructionLabel);
        add(topPanel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        styleButton(addButton, new Color(220, 20, 60));
        styleButton(removeButton, new Color(20, 128, 20));


        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button, Color backgroundColor) {
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
        SwingUtilities.invokeLater(() -> new AddRemoveBlockedMenu(latch));
    }
}
