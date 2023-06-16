package View;

import javax.swing.*;
import utilitaires.Utilitaire;
import dao.daoUser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

public class SignupView extends JFrame {
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton signupButton;

    public SignupView(daoUser userDao) {
        setTitle("Signup");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Set background color to white

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        nameTextField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        signupButton = new JButton("Signup");
        signupButton.setBackground(new Color(144, 238, 144)); // Set button background color to light green

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(nameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(emailTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(signupButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton signInButton = new JButton("Sign In");
        formPanel.add(signInButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String email = emailTextField.getText();
                String password = new String(passwordField.getPassword());
                String role = "employee"; // Set the default role as "employee"

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignupView.this, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean signupSuccess = userDao.signup(name, email, role, password);

                    if (signupSuccess) {
                        JOptionPane.showMessageDialog(SignupView.this, "Signup successful!");
                        dispose(); // Close the signup window
                        openSignInView(userDao); // Open the sign-in window
                    } else {
                        JOptionPane.showMessageDialog(SignupView.this, "Signup failed!", "Error", JOptionPane.ERROR_MESSAGE);
                        nameTextField.setText("");
                        emailTextField.setText("");
                        passwordField.setText("");
                    }
                }
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the signup window
                openSignInView(userDao); // Open the sign-in window
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                openSignInView(userDao); // Open the sign-in window
            }
        });
    }

    private void openSignInView(daoUser userDao) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SigninView signInView = new SigninView(userDao);
                signInView.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Suppose you have already established the database connection
                Connection connection = Utilitaire.seConnecter("C:\\Users\\SYB 2099\\eclipse-workspace\\gestion_conger\\connectionPar.properties");

                // Create an instance of UserDao using the established connection
                daoUser userDao = new daoUser(connection);

                SignupView signupView = new SignupView(userDao);
                signupView.setVisible(true);
            }
        });
    }
}
