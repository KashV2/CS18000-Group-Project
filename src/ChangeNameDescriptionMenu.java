import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class ChangeNameDescriptionMenu extends JFrame implements ActionListener {
    JButton name = new JButton("Change Name");
    JButton desc = new JButton("Change Description");
    JLabel instruction = new JLabel("Would you like to change your name or description?");
    int menuResponse;
    private final CountDownLatch latch;

    public ChangeNameDescriptionMenu(CountDownLatch latch) {
        this.latch = latch;
        setSize(800,500);
        this.setLayout(new FlowLayout());
        name.addActionListener(this);
        desc.addActionListener(this);

        this.add(instruction);
        this.add(name);
        this.add(desc);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == name) {
            menuResponse = 1;
            latch.countDown();
        }
        if (e.getSource() == desc) {
            menuResponse = 2;
            latch.countDown();
        }
        this.dispose();
    }

    public int getMenuResponse() {
        return menuResponse;
    }




}
