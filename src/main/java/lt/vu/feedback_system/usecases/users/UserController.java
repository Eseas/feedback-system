package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.User;
import org.omnifaces.util.Messages;

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
                    user.getEmail(), user.getPassword());
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
    @Transactional
    public void setAdminTrue(){
        try {
            user = userDAO.getUserByEmail(
                    user.getEmail()
            );
            user.setAdmin(true);
        } catch (javax.persistence.NoResultException ex) {
            Messages.addGlobalError("Wrong email");
        }
    }
    @Transactional
    public void setAdminFalse() {
        try {
            user = userDAO.getUserByEmail(
                    user.getEmail()
            );
            user.setAdmin(false);
        } catch (javax.persistence.NoResultException ex) {
            Messages.addGlobalError("Wrong email");
        }
    }
    @Transactional
    public void setBlockedTrue(){
        try {
            user = userDAO.getUserByEmail(
                    user.getEmail()
            );
            user.setBlocked(true);
        }
        catch(javax.persistence.NoResultException ex) {
            Messages.addGlobalError("Wrong email");
        }


    }
    @Transactional
    public void setBlockedFalse() {
        try {
            user = userDAO.getUserByEmail(
                    user.getEmail()
            );
            user.setBlocked(false);
        } catch (javax.persistence.NoResultException ex) {
            Messages.addGlobalError("Wrong email");
        }
    }



}