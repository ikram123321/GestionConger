package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Model.leave;
import dao.daoLeave;
import dao.daoUser;
import utilitaires.Utilitaire;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class AdminView extends JFrame {
    private JComboBox<String> statusComboBox;
    private JButton submitButton;
    private JButton logoutButton; // Logout button
    private JTable leaveTable;
    private DefaultTableModel tableModel;
    private List<leave> leaves;
    private JLabel welcomeLabel;

    public AdminView() {
        setTitle("Admin View");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Set background color to white

        welcomeLabel = new JLabel("Welcome, Admin!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new FlowLayout());

        JLabel statusLabel = new JLabel("Status:");
        String[] statusOptions = {"Pending", "Accept", "Refuse"};
        statusComboBox = new JComboBox<>(statusOptions);

        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(144, 238, 144)); // Set button background color to light green

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(144, 238, 144)); // Set button background color to light green

        formPanel.add(statusLabel);
        formPanel.add(statusComboBox);
        formPanel.add(submitButton);
        formPanel.add(logoutButton);

        add(formPanel, BorderLayout.CENTER);

        // Create table with data model
        String[] columnNames = {"User ID", "Username", "Start Date", "End Date", "Status"};

        tableModel = new DefaultTableModel(columnNames, 0);
        leaveTable = new JTable(tableModel);

        // Set custom cell renderer for coloring
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = (String) table.getValueAt(row, 4);

                if (status.equals("Pending")) {
                    component.setBackground(Color.YELLOW);
                } else if (status.equals("Accept")) {
                    component.setBackground(Color.GREEN);
                } else if (status.equals("Refuse")) {
                    component.setBackground(Color.RED);
                } else {
                    component.setBackground(table.getBackground());
                }

                return component;
            }
        };

        leaveTable.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane scrollPane = new JScrollPane(leaveTable);
        add(scrollPane, BorderLayout.SOUTH);

        // Refresh leave table initially
        refreshLeaveTable();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = leaveTable.getSelectedRow();
                if (selectedRow != -1) {
                    String status = (String) statusComboBox.getSelectedItem();
                    leave leave = leaves.get(selectedRow);
                    int leaveId = leave.getUserId();
                    daoLeave.updateLeaveStatus(leaveId, status);
                    refreshLeaveTable();
                    JOptionPane.showMessageDialog(AdminView.this, "Leave status updated!");
                } else {
                    JOptionPane.showMessageDialog(AdminView.this, "Please select a leave from the table!");
                }
            }
        });

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

    private void refreshLeaveTable() {
        // Clear the table
        tableModel.setRowCount(0);

        // Get all leaves
        leaves = daoLeave.getAllLeaves();

        // Add leaves to the table
        for (leave leave : leaves) {
            // Retrieve the username for the current leave
            String username = daoLeave.getUsernameById(leave.getUserId());

            Object[] rowData = {leave.getUserId(), username, leave.getStartDate(), leave.getEndDate(), leave.getStatus()};
            tableModel.addRow(rowData);
        }

        // Set the column width for the username column
        TableColumnModel columnModel = leaveTable.getColumnModel();
        TableColumn usernameColumn = columnModel.getColumn(1); // Assuming username is at index 1
        usernameColumn.setPreferredWidth(150); // Set the preferred width as needed
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Connection connection = Utilitaire.seConnecter("C:\\Users\\SYB 2099\\eclipse-workspace\\gestion_conger\\connectionPar.properties");
                daoUser userDao = new daoUser(connection);
                daoLeave leaveDao = new daoLeave(connection);
                AdminView adminView = new AdminView();
                adminView.setVisible(true);
            }
        });
    }
}
