import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class SignInMenu extends JFrame implements ActionListener {
    JButton signIn = new JButton("Sign in");
    JButton signUp = new JButton("Sign up");
    JLabel welcomeMessage = new JLabel("Do you want to sign in or create a new account?");
    int output = 0;
    private final CountDownLatch latch;

    public SignInMenu(CountDownLatch latch) {
        this.latch = latch;

        setTitle("Welcome");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        welcomeMessage.setFont(new Font("Arial", Font.BOLD, 20));
        add(welcomeMessage, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        buttonPanel.add(signIn);
        buttonPanel.add(signUp);
        signUp.addActionListener(this);
        signIn.addActionListener(this);
        add(buttonPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void setUnVisible(){
        this.signIn.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signIn) {
            output = 1;
            latch.countDown();
        } else if (e.getSource() == signUp) {
            output = 2;
            latch.countDown();
        }
        this.dispose();
    }

    public int getOutput() {
        return output;
    }
}
