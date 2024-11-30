import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
public class LogInMenu extends JFrame implements ActionListener {
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    String username;
    String password;
    JLabel instruction = new JLabel("Enter your username and password");
    JButton confirm = new JButton("confirm");
    private final CountDownLatch latch;

    public LogInMenu(CountDownLatch latch) {
        this.latch = latch;

        setTitle("Log In");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        usernameField.setPreferredSize(new Dimension(700, 30));
        passwordField.setPreferredSize(new Dimension(700, 30));
        confirm.addActionListener(this);

        this.add(instruction);
        this.add(usernameField);
        this.add(passwordField);
        this.add(confirm);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            username = usernameField.getText();
            password = passwordField.getText();
            latch.countDown();
        }
        this.dispose();
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
