package lt.vu.feedback_system.businesslogic.users;

import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

@SessionScoped
public class Session implements Serializable {

    private static final long serialVersionUID = 1451465415614894L;

    private User user = new User();

    @Inject
    private UserDAO userDAO;

    public void login(String username, String password) {
        try {
            user = userDAO.getUserByEmailAndPassword(
                    username, password);
        } catch (javax.persistence.NoResultException ex) {
        }
    }

    public void logout() {
        user = new User();
    }

    public boolean isLoggedIn() {
        if (user.getId() != null) {
            return true;
        }
        return false;
    }

    public boolean isAdmin() {
        if (user.getId() != null) {
            return user.getAdmin();
        }
        return false;
    }
}
