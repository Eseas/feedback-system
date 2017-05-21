package lt.vu.feedback_system.businesslogic.users;

import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.User;
import lt.vu.feedback_system.utils.generate.PasswordHash;
import lt.vu.feedback_system.utils.generate.PasswordHasher;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

@SessionScoped
public class UserContext implements Serializable {

    private static final long serialVersionUID = 5451465415614894L;

    private Integer id;

    @Inject
    private UserDAO userDAO;

    @Inject
    private SurveyDAO surveyDAO;

    @Inject
    @PasswordHash
    private PasswordHasher passwordHasher;

    public void login(String username, String password) throws IllegalAccessException {
        try {
            User user = userDAO.getUserByEmail(
                    username);

            if (user.getBlocked()) {
                throw new IllegalAccessException();
            }
            if (passwordHasher.check(password, user.getPassword())) {
                id = user.getId();
            }
        } catch (javax.persistence.NoResultException ex) {
        }
    }

    public void logout() {
        id = null;
    }

    public User getUser() {
        if (id != null) {
            return userDAO.getUserById(id);
        }
        return null;
    }

    public boolean isLoggedIn() {
        if (id != null) {
            return true;
        }
        return false;
    }

    public boolean isAdmin() {
        if (id != null) {
            return userDAO.getUserById(id).getAdmin();
        }
        return false;
    }
}
