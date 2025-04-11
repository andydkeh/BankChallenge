package br.com.compass.service;

import br.com.compass.dao.UserDAO;
import br.com.compass.models.Users;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean login (String email, String password) {
        Users user = userDAO.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            return true;
        } else {
            return false;
        }

    }
}
