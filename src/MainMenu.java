import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class MainMenu extends JFrame implements ActionListener {
    JButton edit = new JButton("Edit User Profile");
    JButton exit = new JButton("Exit");
    JButton search = new JButton("Search & View Users");
    private final CountDownLatch latch;
    int menuResponse;


    public MainMenu(CountDownLatch latch) {
        this.latch = latch;

        setSize(800,500);
        this.setLayout(new GridLayout(1,1));
        edit.setPreferredSize(new Dimension(400,200));
        exit.setPreferredSize(new Dimension(400,200));
        search.setPreferredSize(new Dimension(400,200));

        this.add(edit);
        this.add(search);
        this.add(exit);
        edit.addActionListener(this);
        exit.addActionListener(this);
        search.addActionListener(this);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == edit) {
            menuResponse = 1;
            latch.countDown();
        }
        if (e.getSource() == search) {
            menuResponse = 2;
            latch.countDown();
        }
        if (e.getSource() == exit) {
            menuResponse = 3;
            latch.countDown();
        }
        this.dispose();
    }

    public int getMenuResponse() {
        return menuResponse;
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        MainMenu mainMenu = new MainMenu(latch);
    }
}
