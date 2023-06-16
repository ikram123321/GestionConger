package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.leave;

public class daoLeave {
    private static Connection connection;

    public daoLeave(Connection connection) {
        this.connection = connection;
    }

    public static leave getLeaveById(int userId) {
        try {
            String query = "SELECT * FROM leaves WHERE userId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("userId");
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");
                String status = resultSet.getString("status");

                return new leave(startDate, endDate, status, id);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while fetching leave by ID: " + e.getMessage());
        }

        return null;
    }

    public static String getStatusById(int leaveId) {
        try {
            String query = "SELECT status FROM leaves WHERE userId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, leaveId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while fetching leave status by ID: " + e.getMessage());
        }

        return null;
    }

    public static void updateLeaveStatus(int leaveId, String status) {
        try {
            String query = "UPDATE leaves SET status = ? WHERE userId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status);
            statement.setInt(2, leaveId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Leave status updated successfully!");
            } else {
                System.out.println("Leave status update failed!");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during leave status update: " + e.getMessage());
        }
    }

    public static List<leave> getAllLeaves() {
        List<leave> leaves = new ArrayList<>();
        String query = "SELECT * FROM leaves";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("leaveId");
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");
                String status = resultSet.getString("status");
                int userId = resultSet.getInt("userId");
                leave leave = new leave(id, startDate, endDate, status, userId);
                leaves.add(leave);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while fetching all leaves: " + e.getMessage());
        }
        return leaves;
    }

    public static boolean insertLeave(leave newLeave) {
        String query = "INSERT INTO leaves (userId, start_date, end_date, status) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, newLeave.getUserId());
            statement.setString(2, newLeave.getStartDate());
            statement.setString(3, newLeave.getEndDate());
            statement.setString(4, newLeave.getStatus());

            int rowsInserted = statement.executeUpdate();
            return (rowsInserted > 0);
        } catch (SQLException e) {
            System.out.println("An error occurred while inserting leave: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteLeave(int leaveId) {
        String query = "DELETE FROM leaves WHERE userId = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, leaveId);

            int rowsDeleted = statement.executeUpdate();
            return (rowsDeleted > 0);
        } catch (SQLException e) {
            System.out.println("An error occurred while deleting leave: " + e.getMessage());
        }
        return false;
    }
    public static String getUsernameById(int userId) {
        try {
            String query = "SELECT name FROM users WHERE userId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while fetching username by ID: " + e.getMessage());
        }

        return null;
    }
    public static boolean modifierLeavesById(int leaveId, leave modifiedLeave) {
        String query = "UPDATE leaves SET start_date = ?, end_date = ?, status = ? WHERE userId = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, modifiedLeave.getStartDate());
            statement.setString(2, modifiedLeave.getEndDate());
            statement.setString(3, modifiedLeave.getStatus());
            statement.setInt(4, leaveId);

            int rowsUpdated = statement.executeUpdate();
            return (rowsUpdated > 0);
        } catch (SQLException e) {
            System.out.println("An error occurred while modifying leave by ID: " + e.getMessage());
        }
        return false;
    }
}
