package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//this section is mostyl for frontend and basic validations
public class Main {
    private static ReservationManager reservationManager = new ReservationManager();

    public static void main(String[] args) {
        showLoginDialog();
    }

    private static void showLoginDialog() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 250);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setResizable(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon logoIcon = new ImageIcon(Main.class.getClassLoader().getResource("resources/logo.png"));
        Image logoImage = logoIcon.getImage();
        Image resizedLogo = logoImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogo);

        JLabel logoLabel = new JLabel(resizedLogoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");

        customizeTextField(usernameField);
        customizeTextField(passwordField);

        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                passwordField.setEchoChar(checkBox.isSelected() ? '\u0000' : '*');
            }
        });

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (isValidCredentials(username, password)) {
                    loginFrame.dispose();
                    showMenu();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid User/Pass. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }
            }
        });

        panel.add(createLabeledTextField("Username:", usernameField));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createLabeledTextField("Password:", passwordField));
        panel.add(showPasswordCheckBox);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loginButton);

        loginFrame.add(panel, BorderLayout.WEST);
        loginFrame.add(logoLabel, BorderLayout.EAST);

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    private static void customizeTextField(JTextField textField) {
        textField.setBackground(new Color(240, 240, 240));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setMargin(new Insets(5, 5, 5, 5));
        textField.setForeground(new Color(60, 60, 60));
    }

    private static JPanel createLabeledTextField(String label, JTextField textField) {
        JPanel labeledTextFieldPanel = new JPanel(new BorderLayout());
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        labeledTextFieldPanel.add(labelComponent, BorderLayout.NORTH);
        labeledTextFieldPanel.add(textField, BorderLayout.CENTER);
        return labeledTextFieldPanel;
    }

    private static boolean isValidCredentials(String username, String password) {
        return (username.equals("allen") && password.equals("lazatin")) || (username.equals("benken") && password.equals("nebnek")|| (username.equals("urek") && password.equals("pondare")));
    }

    private static void showMenu() {
        JFrame frame = new JFrame("Restaurant Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().setBackground(Color.ORANGE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        JTable reservationTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(reservationTable);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JLabel totalCountLabel = new JLabel("Total Reservations: 0");
        frame.getContentPane().add(totalCountLabel, BorderLayout.NORTH);

        reservationManager.setReservationTable(reservationTable);
        reservationManager.setTotalCountLabel(totalCountLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));

        ImageIcon welcomeImageIcon = new ImageIcon(Main.class.getClassLoader().getResource("resources/Welcome.png"));
        Image originalImage = welcomeImageIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        ImageIcon resizedWelcomeImageIcon = new ImageIcon(resizedImage);
        JLabel welcomeImageLabel = new JLabel(resizedWelcomeImageIcon);
        welcomeImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        imagePanel.add(Box.createHorizontalGlue());
        imagePanel.add(welcomeImageLabel);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton viewReservationsButton = MenuButton("View Reservations");
        JButton makeReservationButton = MenuButton("Make Reservation");
        JButton deleteReservationButton = MenuButton("Delete Reservation");
        JButton generateReportButton = MenuButton("Generate Report");
        JButton exitButton = MenuButton("Exit");

        viewReservationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservationManager.viewAllReservations();
            }
        });

        makeReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeReservation();
            }
        });

        deleteReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteReservation();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitImage(frame);
            }
        });

        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservationManager.generateReservationReport();
            }
        });

        menuPanel.add(viewReservationsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(makeReservationButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(deleteReservationButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(generateReportButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(exitButton);

        mainPanel.add(menuPanel, BorderLayout.CENTER);
        mainPanel.add(Box.createHorizontalStrut(20));
        mainPanel.add(imagePanel, BorderLayout.EAST);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JButton MenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(new Color(255, 140, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 140, 0), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Button action handling
            }
        });

        return button;
    }

    private static void makeReservation() {
        JTextField nameField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JSpinner adultsSpinner = new JSpinner();
        JSpinner childrenSpinner = new JSpinner();

        Object[] fields = {"Name:", nameField, "Date:", dateField, "Time:", timeField, "No of Adults: 500PHP", adultsSpinner, "No of Children: 300PHP", childrenSpinner};
        int option = JOptionPane.showConfirmDialog(null, fields, "Make Reservation", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String date = dateField.getText();
            String time = timeField.getText();
            int adults = (int) adultsSpinner.getValue();
            int children = (int) childrenSpinner.getValue();

           
            if (children == 1 && adults == 0) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid input.", "Error", JOptionPane.ERROR_MESSAGE);
                makeReservation(); 
            } else {
                if (adults < 0 || children < 0) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid input.", "Error", JOptionPane.ERROR_MESSAGE);
                    makeReservation();
                } else {
                    reservationManager.addReservation(name, date, time, adults, children);
                    JOptionPane.showMessageDialog(null, "Reservation added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private static void deleteReservation() {
        if (reservationManager.getReservationCount() == 0) {
            JOptionPane.showMessageDialog(null, "No reservations yet.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return; 
        }

        String[] options = new String[reservationManager.getReservationCount()];
        for (int i = 0; i < reservationManager.getReservationCount(); i++) {
            options[i] = Integer.toString(i + 1);
        }

        String reservationNumber = (String) JOptionPane.showInputDialog(
                null,
                "Select reservation number to delete",
                "Delete Reservation",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (reservationNumber != null) {
            int reservationIndex = Integer.parseInt(reservationNumber);
            reservationManager.deleteReservation(reservationIndex);
        }
    }


    private static void showExitImage(JFrame parentFrame) {
        JFrame confirmationFrame = new JFrame("Restaurant Reservation System");
        confirmationFrame.setSize(400, 350);
        confirmationFrame.setLayout(new BorderLayout());

        ImageIcon originalIcon = new ImageIcon(Main.class.getClassLoader().getResource("resources/amp.png"));
        Image img = originalIcon.getImage();
        Image resizedImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);

        JLabel messageLabel = new JLabel("<html><center>Thanks for using my program!<br>Total Reservations: " + reservationManager.getReservationCount() + "</center></html>");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageLabel, BorderLayout.NORTH);

        messagePanel.add(new JLabel(resizedIcon), BorderLayout.CENTER);

        confirmationFrame.add(messagePanel, BorderLayout.CENTER);

        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmationFrame.dispose();

                JFrame additionalMessageFrame = new JFrame("Goodluck Have Fun!");
                additionalMessageFrame.setSize(400, 300);
                additionalMessageFrame.setLayout(new BorderLayout());

                ImageIcon additionalImageIcon = new ImageIcon(Main.class.getClassLoader().getResource("resources/superteam.png"));
                Image additionalImage = additionalImageIcon.getImage();
                Image resizedAdditionalImage = additionalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                ImageIcon resizedAdditionalImageIcon = new ImageIcon(resizedAdditionalImage);

                JLabel additionalMessageLabel = new JLabel("<html><center>Salamat! Gumawa: Lazatin, Allen S. ITE-231<br>Sa susunod nalang po ulit!</center></html>");
                additionalMessageLabel.setHorizontalAlignment(JLabel.CENTER);

                JPanel additionalMessagePanel = new JPanel(new BorderLayout());
                additionalMessagePanel.add(additionalMessageLabel, BorderLayout.NORTH);
                additionalMessagePanel.add(new JLabel(resizedAdditionalImageIcon), BorderLayout.CENTER);

                additionalMessageFrame.add(additionalMessagePanel, BorderLayout.CENTER);
                additionalMessageFrame.setLocationRelativeTo(parentFrame);
                additionalMessageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                additionalMessageFrame.setVisible(true);
            }
        });

        confirmationFrame.add(yesButton, BorderLayout.SOUTH);

        confirmationFrame.setLocationRelativeTo(parentFrame);
        confirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        confirmationFrame.setVisible(true);
    }
}
