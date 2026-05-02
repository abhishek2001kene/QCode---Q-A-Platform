package Service;

import DAO.UserDAO;
import Model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public void register(String name, String email, String password) {
        User user = new User(name, email, password);
        userDAO.saveUser(user);
    }

    public User login(String email, String password) {
        return userDAO.login(email, password);
    }
}