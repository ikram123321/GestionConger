package Controler;

import dao.daoUser;
import Model.user;

public class SigninController {
    private daoUser daoUser;

    public SigninController(daoUser daoUser) {
        this.daoUser = daoUser;
    }

    public user signin(String username, String password) {
        user user = daoUser.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            // Perform signin logic here
            return user;
        }

        return null;
    }
}
