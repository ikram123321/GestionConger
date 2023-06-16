package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.leave;
import Model.user;

public class daoUser {
    private static Connection connection;

    public daoUser(Connection connection) {
        this.connection = connection;    }

    public boolean signup(String name, String email, String role, String password) {
        try {
            // Prepare the insertion query
            String query = "INSERT INTO users (name, email, role, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, role);
            statement.setString(4, password);

            // Execute the insertion query
            int rowsAffected = statement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                System.out.println("Signup successful!");
                return true;
            } else {
                System.out.println("Signup failed!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during signup: " + e.getMessage());
            return false;
        }
    }

    public user getUserByUsername(String username) {
        try {
            String query = "SELECT * FROM users WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                String password = resultSet.getString("password");

                return new user(id, name, email, role, password);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while fetching user by username: " + e.getMessage());
        }

        return null;
    }

  
    public user signin(String email, String password) {
        try {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");

               return new user(id, name, email, role, password);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during signin: " + e.getMessage());
        }

       return null;
    }

	public boolean deleteLeaveRequest(int leaveId) {
		// TODO Auto-generated method stub
		return false;
	} public static user getUserById(int userId) {
        try {
            String query = "SELECT * FROM users WHERE userId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                String password = resultSet.getString("password");

                return new user(id, name, email, role, password);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while fetching user by ID: " + e.getMessage());
        }

        return null;
    }

}
