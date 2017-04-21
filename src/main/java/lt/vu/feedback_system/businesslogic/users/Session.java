package lt.vu.feedback_system.businesslogic.users;

import lombok.Getter;
import lt.vu.feedback_system.dao.SurveyDAO;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.Survey;
import lt.vu.feedback_system.entities.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

@SessionScoped
public class Session implements Serializable {

    private static final long serialVersionUID = 5451465415614894L;

    @Getter
    private User user = new User();

    @Inject
    private UserDAO userDAO;

    @Inject
    private SurveyDAO surveyDAO;

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

    public boolean isSurveyCreator(Integer surveyId) {
        try {
            Survey survey = surveyDAO.getSurveyById(surveyId);

            if (user.getId().equals(survey.getCreator().getId())) {
                return true;
            }
        } catch (javax.persistence.NoResultException ex) {
        } catch (NullPointerException ex) {
        }

        return false;
    }
}
