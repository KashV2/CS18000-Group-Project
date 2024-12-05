import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class SearchUserMenu extends JFrame implements ActionListener {
    JButton search = new JButton("Search");
    JTextField username = new JTextField();
    JButton addOrRemove = new JButton("Add or Remove Friend");
    JButton blockOrUnblock = new JButton("Block Or Unblock User");
    JButton message = new JButton("Message");
    JButton back = new JButton("Back");
    JLabel userNotFound = new JLabel("User not found");
    JLabel searchedUserInfo = new JLabel("-");
    JPanel buttonPanel = new JPanel();
    int menuResponse;
    String searchedUser;
    private final CyclicBarrier barrier;


    public SearchUserMenu(CyclicBarrier barrier) {
        this.barrier = barrier;

        setSize(800,500);
        this.setLayout(new FlowLayout());
        username.setPreferredSize(new Dimension(700,30));
        this.setLayout(new FlowLayout());
        search.addActionListener(this);
        addOrRemove.addActionListener(this);
        blockOrUnblock.addActionListener(this);
        message.addActionListener(this);
        back.addActionListener(this);

        this.add(search);
        this.add(back);
        this.add(username);
        this.add(buttonPanel);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            searchedUser = username.getText();
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == addOrRemove) {
            menuResponse = 1;
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == blockOrUnblock) {
            menuResponse = 2;
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == message) {
            menuResponse = 3;
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == back) {
            menuResponse = 4;
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void displayUserNotFound() {
        userNotFound.setVisible(true);
        userNotFound.setForeground(Color.RED);
        this.add(userNotFound);
        this.revalidate();
        this.repaint();

        new Timer(1000, e -> {
            userNotFound.setVisible(false);
            this.revalidate();
            this.repaint();
        }).start();
    }

    public void userActionMenu(String userInfo) {
        searchedUserInfo.setText(userInfo);
        this.add(searchedUserInfo);
        buttonPanel.add(addOrRemove);
        buttonPanel.add(blockOrUnblock);
        buttonPanel.add(message);

        this.revalidate();
        this.repaint();
    }

    public String getSearchedUser() {
        return searchedUser;
    }

    public int getMenuResponse() {
        return menuResponse;
    }

    public static void main(String[] args) throws InterruptedException {


    }
}
