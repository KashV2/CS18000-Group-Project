import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class MainMenu extends JFrame implements ActionListener {
    private final JButton editButton = new JButton("Edit User Profile");
    private final JButton searchButton = new JButton("Search & View Users");
    private final JButton exitButton = new JButton("Exit");
    private final CountDownLatch latch;
    private int menuResponse;

    public MainMenu(CountDownLatch latch) {
        this.latch = latch;

        setTitle("Main Menu");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JLabel headerLabel = new JLabel("Main Menu", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setForeground(new Color(50, 50, 50));
        add(headerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.add(createStyledButton(editButton));
        buttonPanel.add(createStyledButton(searchButton));
        buttonPanel.add(createStyledButton(exitButton));
        add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setBackground(new Color(135, 206, 250));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            menuResponse = 1;
            latch.countDown();
        } else if (e.getSource() == searchButton) {
            menuResponse = 2;
            latch.countDown();
        } else if (e.getSource() == exitButton) {
            menuResponse = 3;
            latch.countDown();
        }
        dispose();
    }

    public int getMenuResponse() {
        return menuResponse;
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        new MainMenu(latch);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
