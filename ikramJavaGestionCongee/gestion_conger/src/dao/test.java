package dao;

import java.sql.Connection;
import utilitaires.Utilitaire;
import javax.swing.SwingUtilities;
import View.SignupView;
import dao.daoLeave;
import Model.leave;

public class test {
    public static void main(String[] args) {
        Connection connection = Utilitaire.seConnecter("C:\\Users\\SYB 2099\\eclipse-workspace\\gestion_conger\\connectionPar.properties");

        daoUser userDao = new daoUser(connection);
        daoLeave leaveDao = new daoLeave(connection);

        // Create a new leave object
       

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SignupView signupView = new SignupView(userDao);
                signupView.setVisible(true);
            }
        });
    }
}
