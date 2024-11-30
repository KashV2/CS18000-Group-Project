import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class AddRemoveFriendMenu extends JFrame implements ActionListener {
    JButton remove = new JButton("Remove Friend");
    JButton add = new JButton("Add Friend");
    JLabel instruction = new JLabel("Would you like to add or remove a friend? ");
    int menuResponse;
    private final CountDownLatch latch;

    public AddRemoveFriendMenu(CountDownLatch latch) {
        this.latch = latch;
        setSize(800,500);
        this.setLayout(new FlowLayout());
        add.addActionListener(this);
        remove.addActionListener(this);

        this.add(instruction);
        this.add(remove);
        this.add(add);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            menuResponse = 1;
            latch.countDown();
        }
        if (e.getSource() == remove) {
            menuResponse = 2;
            latch.countDown();
        }
        this.dispose();
    }

    public int getMenuResponse() {
        return menuResponse;
    }




}
