package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.daoLeave;
import dao.daoUser;
import utilitaires.Utilitaire;
import Model.user;
import Model.leave;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class DemandeCongerView extends JFrame {
	 private JTextField startDateTextField;
	    private JTextField endDateTextField;
	    private JButton requestButton;
	    private JButton modifyButton;
	    private JButton deleteButton;
	    private JTable leaveTable;
	    private DefaultTableModel tableModel;
	    private JButton logoutButton;
	    private JLabel greetingLabel;
    private static daoUser daoUser;
    private static daoLeave daoLeave;
    private static user currentUser;

    public DemandeCongerView(daoUser daoUser, daoLeave daoLeave, user currentUser) {
        this.daoUser = daoUser;
        this.daoLeave = daoLeave;
        this.currentUser = currentUser;

        setTitle("Demande de Congé");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        logoutButton = new JButton("Logout"); // Create the logout button

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(logoutButton, gbc);
        gbc.insets = new Insets(5, 5, 5, 5);
        greetingLabel = new JLabel("Hello, " + currentUser.getName() + "!"); // Create the greeting label
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(greetingLabel, gbc);
        
        JLabel startDateLabel = new JLabel("Date de début (AAAA-MM-JJ):");
        startDateTextField = new JTextField(20);

        JLabel endDateLabel = new JLabel("Date de fin (AAAA-MM-JJ):");
        endDateTextField = new JTextField(20);

        requestButton = new JButton("Envoyer la demande");
        modifyButton = new JButton("Modifier la demande");
        deleteButton = new JButton("Supprimer la demande");

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(startDateLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(startDateTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(endDateLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        formPanel.add(endDateTextField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        formPanel.add(requestButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        formPanel.add(modifyButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        formPanel.add(deleteButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Date de début");
        tableModel.addColumn("Date de fin");
        tableModel.addColumn("Statut");
        leaveTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(leaveTable);
        add(tableScrollPane, BorderLayout.CENTER);

        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateTextField.getText();
                String endDate = endDateTextField.getText();
                String status = "Pending"; // Default status for new leave requests

                
                
                
                // Create a new leave object
                leave newLeave = new leave(startDate, endDate, status, currentUser.getId());

                // Save the leave request
                boolean success = daoLeave.insertLeave(newLeave);

                if (success) {
                    JOptionPane.showMessageDialog(DemandeCongerView.this, "Demande de congé envoyée avec succès!");
                    // Refresh the leave table
                    refreshLeaveTable();
                } else {
                    JOptionPane.showMessageDialog(DemandeCongerView.this, "Échec de l'envoi de la demande de congé!", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                // Clear the input fields
                startDateTextField.setText("");
                endDateTextField.setText("");
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = leaveTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DemandeCongerView.this, "Veuillez sélectionner une demande de congé à modifier!", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int leaveId = (int) leaveTable.getValueAt(selectedRow, 0);
                String startDate = startDateTextField.getText();
                String endDate = endDateTextField.getText();
                String status = "Pending"; // Default status for new leave requests

                // Create a new leave object
                leave modifiedLeave = new leave(startDate, endDate, status, currentUser.getId());

                // Modify the leave request
                boolean success = daoLeave.modifierLeavesById(leaveId, modifiedLeave);

                if (success) {
                    JOptionPane.showMessageDialog(DemandeCongerView.this, "Demande de congé modifiée avec succès!");
                    // Refresh the leave table
                    refreshLeaveTable();
                } else {
                    JOptionPane.showMessageDialog(DemandeCongerView.this, "Échec de la modification de la demande de congé!", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                // Clear the input fields
                startDateTextField.setText("");
                endDateTextField.setText("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = leaveTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DemandeCongerView.this, "Veuillez sélectionner une demande de congé à supprimer!", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int leaveId = (int) leaveTable.getValueAt(selectedRow, 0);

                // Delete the leave request
                boolean success = daoLeave.deleteLeave(leaveId);

                if (success) {
                    JOptionPane.showMessageDialog(DemandeCongerView.this, "Demande de congé supprimée avec succès!");
                    // Refresh the leave table
                    refreshLeaveTable();
                } else {
                    JOptionPane.showMessageDialog(DemandeCongerView.this, "Échec de la suppression de la demande de congé!", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                // Clear the input fields
                startDateTextField.setText("");
                endDateTextField.setText("");
            }
        });

        // Populate the leave table
        refreshLeaveTable();
    }

    private void refreshLeaveTable() {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the leave for the user
        leave leave = daoLeave.getLeaveById(currentUser.getId());

        // Check if leave is null
        if (leave == null) {
            // Create an empty leave object
            leave = new leave("", "", "", currentUser.getId());
        }

        // Add the leave to the table
        Object[] rowData = { leave.getUserId(), leave.getStartDate(), leave.getEndDate(), leave.getStatus() };
        tableModel.addRow(rowData);

        // Color the rows based on the leave status
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String status = (String) tableModel.getValueAt(i, 3);
            Color rowColor;
            if (status == null) {
                rowColor = Color.WHITE; // Set row color to white if status is null
            } else if (status.equals("Pending")) {
                rowColor = Color.YELLOW;
            } else if (status.equals("Accept")) {
                rowColor = Color.GREEN;
            } else {
                rowColor = Color.RED;
            }
            leaveTable.setSelectionBackground(rowColor);
            leaveTable.setSelectionForeground(Color.BLACK);
        }

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                openSignupView(); // Open the SignupView
            }
        });
    
    }


    private void openSignupView() {
        SignupView signupView = new SignupView(null);
        signupView.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Connection connection = Utilitaire.seConnecter("C:\\Users\\SYB 2099\\eclipse-workspace\\gestion_conger\\connectionPar.properties");
                daoUser userDao = new daoUser(connection);
                daoLeave leaveDao = new daoLeave(connection);
                DemandeCongerView demandeCongerView = new DemandeCongerView(userDao, leaveDao, currentUser);
                demandeCongerView.setVisible(true);
            }
        });
    }

}
