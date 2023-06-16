package utilitaires;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Utilitaire {
    private static Connection cn = null;

    public static Connection seConnecter(String chemin) {
        try {
            if (cn == null) {
                Properties p = new Properties();
                FileInputStream f = new FileInputStream(chemin);
                p.load(f);
                String driver, url, login, pwd;
                driver = p.getProperty("driver");
                url = p.getProperty("url");
                login = p.getProperty("login");
                pwd = p.getProperty("pwd");
               
                Class.forName(driver); // Loading the driver
                cn = DriverManager.getConnection(url, login, pwd); // Opening a connection
                System.out.println("Connection established");
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
        return cn;
    }

    public static ResultSet OuvrirReq(String req) {
        ResultSet res = null;
        try {
            if (cn != null) {
                Statement state = cn.createStatement();
                res = state.executeQuery(req);
            } else
                System.out.println("Connection not initialized");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
           return res;
        }
    }

    public static Connection getConnection() {
        return cn;
    }

    public static void seDeconnecter() {
        try {
            cn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
