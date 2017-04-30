package lt.vu.feedback_system.businesslogic.users;

import lombok.Getter;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.User;

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

    public void login(String username, String password) {
        try {
            id = userDAO.getUserByEmailAndPassword(
                    username, password).getId();
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

    public boolean isSurveyCreator(Integer surveyId) {
        try {
            User surveyCreator = surveyDAO.getSurveyById(surveyId).getCreator();

            if (id.equals(surveyCreator.getId())) {
                return true;
            }
        } catch (javax.persistence.NoResultException ex) {
        } catch (NullPointerException ex) {
        }

        return false;
    }
}
