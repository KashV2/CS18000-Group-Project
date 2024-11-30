import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class NameDescChangeMenu extends JFrame implements ActionListener {
    JButton sendButton = new JButton("Change");
    JLabel label = new JLabel();
    JTextField textField = new JTextField();
    String message;
    private final CountDownLatch latch;

    public NameDescChangeMenu(CountDownLatch latch, String message) {
        this.latch = latch;
        setSize(800,500);
        this.setLayout(new FlowLayout());
        textField.setPreferredSize(new Dimension(600, 100));
        label.setText(message);
        sendButton.addActionListener(this);

        this.add(label);
        this.add(textField);
        this.add(sendButton);
        this.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            message = textField.getText();
            latch.countDown();
        }
        this.dispose();
    }

    public String getMessage() {
        return message;
    }
}
