import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class ChangeNameDescriptionMenu extends JFrame implements ActionListener {
    private final JButton nameButton = new JButton("Change Name");
    private final JButton descButton = new JButton("Change Description");
    private final JLabel instruction = new JLabel("Choose an option");
    private int menuResponse;
    private final CountDownLatch latch;

    public ChangeNameDescriptionMenu(CountDownLatch latch) {
        this.latch = latch;

        setTitle("Change Options");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // Instruction label at the top
        instruction.setFont(new Font("SansSerif", Font.BOLD, 16));
        instruction.setHorizontalAlignment(SwingConstants.CENTER);
        add(instruction, BorderLayout.NORTH);

        // Buttons in the center
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        nameButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descButton.setFont(new Font("SansSerif", Font.PLAIN, 14));

        nameButton.setBackground(new Color(70, 130, 180));
        nameButton.setForeground(Color.WHITE);
        nameButton.setFocusPainted(false);

        descButton.setBackground(new Color(70, 130, 180));
        descButton.setForeground(Color.WHITE);
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

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        new ChangeNameDescriptionMenu(latch);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
