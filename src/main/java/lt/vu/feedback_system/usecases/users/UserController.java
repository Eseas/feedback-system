package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.User;
import org.mindrot.jbcrypt.BCrypt;
import org.omnifaces.util.Messages;
import org.primefaces.component.password.Password;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;


@Named
@SessionScoped
@Slf4j
public class UserController implements Serializable {

    private static final String ON_LOGIN_REDIRECT = "index?faces-redirect=true";
    private static final String ON_LOGIN_FAIL_REDIRECT = "login?faces-redirect=true&error=";

    @Getter
    private User user = new User();
    @Inject
    private UserDAO userDAO;

    public String doLogin() {
        try {
            user = userDAO.getUserByEmailAndPassword(
                    user.getEmail(), getUserPassword());
        } catch (javax.persistence.NoResultException ex) {
            return ON_LOGIN_FAIL_REDIRECT;
        }

        if (user != null && user.getId() != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("user", user);

            return ON_LOGIN_REDIRECT;
        }

        return ON_LOGIN_FAIL_REDIRECT;
    }

    public String doLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return ON_LOGIN_REDIRECT;
    }

    private String getUserPassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        Password pw = (Password)context.getViewRoot().findComponent("login-form:user-password");
        // dummy result for testing
        return (String)pw.getValue();
        // actual result
        // return BCrypt.hashpw((String)pw.getValue(), BCrypt.gensalt());
    }

}