import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class LogInMenu extends JFrame implements ActionListener {
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private String username;
    private String password;
    private final JLabel instruction = new JLabel("Enter your username and password", JLabel.CENTER);
    private final JButton confirm = new JButton("Confirm");
    private final CountDownLatch latch;

    public LogInMenu(CountDownLatch latch) {
        this.latch = latch;

        setTitle("Log In");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // Instruction Panel
        instruction.setFont(new Font("SansSerif", Font.BOLD, 18));
        instruction.setForeground(new Color(50, 50, 50));
        add(instruction, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        inputPanel.add(usernameField);
        inputPanel.add(passwordField);
        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirm.setFont(new Font("SansSerif", Font.PLAIN, 16));
        confirm.setBackground(new Color(135, 206, 250));
        confirm.setForeground(Color.WHITE);
        confirm.setFocusPainted(false);
        confirm.addActionListener(this);
        buttonPanel.add(confirm);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            username = usernameField.getText();
            password = new String(passwordField.getPassword());
            latch.countDown();
        }
        dispose();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        LogInMenu logInMenu = new LogInMenu(latch);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(logInMenu.getUsername());
        System.out.println(logInMenu.getPassword());
    }
}
