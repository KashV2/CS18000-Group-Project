import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

/**
 * The SignInMenu class. This is the menu that appears when we are trying to sign in to our app.
 * <p>
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Gia Le
 * @version December 7, 2024
 */

public class SignInMenu extends JFrame implements ActionListener, SignInMenuInterface {
    private final JButton signInButton;
    private final JButton signUpButton;
    private final CountDownLatch latch;
    private int output;

    public SignInMenu(CountDownLatch latch) {
        this.latch = latch;

        setTitle("Welcome");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JLabel welcomeMessage = new JLabel("Welcome! Please choose an option.", JLabel.CENTER);
        welcomeMessage.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeMessage.setForeground(new Color(50, 50, 50));
        add(welcomeMessage, BorderLayout.NORTH);

        signInButton = createGradientButton("Sign In", new Color(135, 206, 250), new Color(70, 130, 180));
        signUpButton = createGradientButton("Sign Up", new Color(255, 182, 193), new Color(255, 105, 180));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.add(signInButton);
        buttonPanel.add(signUpButton);
        add(buttonPanel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("", JLabel.CENTER);
        footerLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(100, 100, 100));
        add(footerLabel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton createGradientButton(String text, Color startColor, Color endColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        button.addActionListener(this);
        return button;
    }

    public void setSignInButtonVisible(boolean isVisible) {
        signInButton.setVisible(isVisible);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signInButton) output = 1;
        else if (e.getSource() == signUpButton) output = 2;
        latch.countDown();
        dispose();
    }

    public int getOutput() {
        return output;
    }
}
