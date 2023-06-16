package View;

import javax.swing.*;
import Model.user;
import dao.daoUser;
import utilitaires.Utilitaire;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

public class SigninView extends JFrame {
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton signinButton;
    private JButton adminButton;

    private daoUser userDao;
    private user currentUser;

    // Admin password
    private static final String ADMIN_PASSWORD = "admin123";

    public SigninView(daoUser userDao) {
        this.userDao = userDao;

        setTitle("Signin");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Set background color to white

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        signinButton = new JButton("Signin");
        signinButton.setBackground(new Color(144, 238, 144)); // Set button background color to light green

        adminButton = new JButton("Are you admin?");
        adminButton.setBackground(new Color(144, 238, 144)); // Set button background color to light green

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(emailTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(signinButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(adminButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = new String(passwordField.getPassword());

                currentUser = userDao.signin(email, password);

                if (currentUser != null) {
                    JOptionPane.showMessageDialog(SigninView.this, "Signin successful!");
                    dispose();
                    openDemandeCongerView();
                } else {
                    JOptionPane.showMessageDialog(SigninView.this, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
                    emailTextField.setText("");
                    passwordField.setText("");
                }
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredPassword = JOptionPane.showInputDialog(SigninView.this, "Enter admin password:");

                if (enteredPassword != null && enteredPassword.equals(ADMIN_PASSWORD)) {
                    dispose();
                    openAdminView();
                } else {
                    JOptionPane.showMessageDialog(SigninView.this, "Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                openSignupView();
            }
        });
    }

    private void openDemandeCongerView() {
        DemandeCongerView demandeCongerView = new DemandeCongerView(userDao, null, currentUser);
        demandeCongerView.setVisible(true);
    }

    private void openSignupView() {
        SignupView signupView = new SignupView(userDao);
        signupView.setVisible(true);
    }

    private void openAdminView() {
        AdminView adminView = new AdminView();
        adminView.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Connection connection = Utilitaire.seConnecter("C:\\Users\\SYB 2099\\eclipse-workspace\\gestion_conger\\connectionPar.properties");
                daoUser userDao = new daoUser(connection);
                SigninView signinView = new SigninView(userDao);
                signinView.setVisible(true);
            }
        });
    }
}
