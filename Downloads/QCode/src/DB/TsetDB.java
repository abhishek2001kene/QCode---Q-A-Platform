//import DAO.UserDAO;
package DB;

import DAO.UserDAO;
import Model.User;

public class TsetDB {
    public static void main(String[] args) {

        UserDAO dao = new UserDAO();

        // ✅ TEST INSERT
        User user = new User("abhi", "abhi@gmail.com", "123");
        dao.saveUser(user);

        // ✅ TEST LOGIN
        User loggedUser = dao.login("abhi@gmail.com", "123");

        if (loggedUser != null) {
            System.out.println("✅ Login Success");
            System.out.println("Name: " + loggedUser.getName());
        } else {
            System.out.println("❌ Login Failed");
        }
    }
}