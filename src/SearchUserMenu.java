import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * The SearchUserMenu class. This is the menu that appears when we are trying to search for a user
 *
 * Purdue University -- CS18000 -- Fall 2024 -- Team Project
 *
 * @author Rong Yang
 * @author Bach Gia Le
 * @version December 7, 2024
 */

public class SearchUserMenu extends JFrame implements ActionListener, SearchUserMenuInterface {
    private final JButton searchButton = new JButton("Search");
    private final JTextField usernameField = new JTextField();
    private final JButton addOrRemoveButton = new JButton("Add/Remove Friend");
    private final JButton blockOrUnblockButton = new JButton("Block/Unblock User");
    private final JButton messageButton = new JButton("Message");
    private final JButton backButton = new JButton("Back");
    private final JLabel userNotFoundLabel = new JLabel("User not found", JLabel.CENTER);
    private final JLabel userInfoLabel = new JLabel("");
    private final JPanel actionButtonPanel = new JPanel();
    private int menuResponse;
    private String searchedUser;
    private final CyclicBarrier barrier;
    private boolean backPressed = false;

    public SearchUserMenu(CyclicBarrier barrier) {
        this.barrier = barrier;

        // Frame setup
        setTitle("Search User Menu");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        // Top Panel: Search Bar
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        JLabel searchLabel = new JLabel("Enter username to search:", JLabel.CENTER);
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        topPanel.add(searchLabel, BorderLayout.NORTH);

        usernameField.setPreferredSize(new Dimension(300, 30));
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        searchButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(this);

        JPanel searchBarPanel = new JPanel();
        searchBarPanel.add(usernameField);
        searchBarPanel.add(searchButton);
        topPanel.add(searchBarPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel: User Information and Action Buttons
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        userInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userInfoLabel.setForeground(Color.DARK_GRAY);
        userInfoLabel.setVisible(false);

        JPanel userInfoPanel = new JPanel(new BorderLayout());
        userInfoPanel.add(userInfoLabel, BorderLayout.WEST);
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(userInfoPanel, BorderLayout.NORTH);

        userNotFoundLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userNotFoundLabel.setForeground(Color.RED);
        userNotFoundLabel.setVisible(false);
        centerPanel.add(userNotFoundLabel, BorderLayout.CENTER);

        actionButtonPanel.setLayout(new GridLayout(1, 3, 10, 10));
        actionButtonPanel.setVisible(false);
        styleButton(addOrRemoveButton, new Color(85, 107, 47));
        styleButton(blockOrUnblockButton, new Color(255, 140, 0));
        styleButton(messageButton, new Color(135, 206, 250));
        actionButtonPanel.add(addOrRemoveButton);
        actionButtonPanel.add(blockOrUnblockButton);
        actionButtonPanel.add(messageButton);
        centerPanel.add(actionButtonPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel: Back Button
        JPanel bottomPanel = new JPanel();
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(this);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void styleButton(JButton button, Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchedUser = usernameField.getText().trim();
            if (searchedUser.isEmpty()) {
                displayUserNotFound();
            } else {
                try {
                    barrier.await();
                    this.searchButton.setVisible(false);
                } catch (InterruptedException | BrokenBarrierException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == addOrRemoveButton) {
            menuResponse = 1;
            triggerBarrier();
        } else if (e.getSource() == blockOrUnblockButton) {
            menuResponse = 2;
            triggerBarrier();
        } else if (e.getSource() == messageButton) {
            menuResponse = 3;
            triggerBarrier();
        } else if (e.getSource() == backButton) {
            menuResponse = 4;
            backPressed = true;
            triggerBarrier();
        }
    }

    public void triggerBarrier() {
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            ex.printStackTrace();
        }
    }

    public void displayUserNotFound() {
        userNotFoundLabel.setVisible(true);
        userInfoLabel.setVisible(false);
        actionButtonPanel.setVisible(false);

        revalidate();
        repaint();

        new Timer(2000, e -> {
            userNotFoundLabel.setVisible(false);
            revalidate();
            repaint();
        }).start();
    }

    public boolean isBackPressed() {
        return backPressed;
    }

    public void userActionMenu(String userInfo) {
        userNotFoundLabel.setVisible(false);
        userInfoLabel.setText("User Info: " + userInfo);
        userInfoLabel.setVisible(true);
        actionButtonPanel.setVisible(true);

        revalidate();
        repaint();
    }

    public String getSearchedUser() {
        return searchedUser;
    }

    public int getMenuResponse() {
        return menuResponse;
    }

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(1);
        SwingUtilities.invokeLater(() -> new SearchUserMenu(barrier));
    }
}
