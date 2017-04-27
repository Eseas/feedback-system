package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.dao.ChangePwKeyDAO;
import lt.vu.feedback_system.dao.PotentialUserDAO;
import lt.vu.feedback_system.dao.RegKeyDAO;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.ChangePwKey;
import lt.vu.feedback_system.entities.PotentialUser;
import lt.vu.feedback_system.entities.RegKey;
import lt.vu.feedback_system.entities.User;
import lt.vu.feedback_system.utils.security.PasswordHasher;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Slf4j
@RequestScoped
@Named("completeKeyController")
public class CompleteKeyController {

    private RegKeyDAO regKeyDAO;

    private ChangePwKeyDAO changePwKeyDAO;

    private PotentialUserDAO potentialUserDAO;

    private UserDAO userDAO;

    private PasswordHasher pwHasher;

    @Getter
    @Setter
    private String code;

    protected CompleteKeyController() {}

    @Inject
    private CompleteKeyController(RegKeyDAO regKeyDAO,
                                  ChangePwKeyDAO changePwKeyDAO,
                                  PotentialUserDAO potentialUserDAO,
                                  UserDAO userDAO,
                                  PasswordHasher pwHasher) {
        this.regKeyDAO = regKeyDAO;
        this.changePwKeyDAO = changePwKeyDAO;
        this.potentialUserDAO = potentialUserDAO;
        this.userDAO = userDAO;
        this.pwHasher = pwHasher;
    }

    @Transactional
    public void register() {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        try {
            final RegKey regKey = regKeyDAO.selectByCode(code);
            if (regKey.getExpires().compareTo(LocalDateTime.now()) == 1) {
                final PotentialUser potentialUser = regKey.getUser();

                final String firstName = ((InputText)context.getViewRoot().findComponent("complete-reg-form:first-name")).getValue().toString();
                final String lastName = ((InputText)context.getViewRoot().findComponent("complete-reg-form:last-name")).getValue().toString();
                //TODO: pakeisti į hash'intą password'ą
//            final String password = pwHasher.hash(((Password)context.getViewRoot().findComponent("complete-reg-form:password")).getValue().toString());
                final String password = ((Password)context.getViewRoot().findComponent("complete-reg-form:password")).getValue().toString();
                final String email = potentialUser.getEmail();
                final User user = new User(firstName, lastName, email, password, false, false);

                regKeyDAO.delete(regKey);
                regKeyDAO.deleteByUserId(potentialUser.getId());
                potentialUserDAO.delete(potentialUser);
                userDAO.create(user);

                msg = new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Success",
                        "Successfully registered");
                log.info(String.format("registration with key code '%s' for email '%s' successful", code, email));
            } else {
                regKeyDAO.delete(regKey);
                msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Error",
                        "Registration has expired link expired. " +
                                "Visit registration page and send another registration link. ");
                log.info(String.format("registration with key code '%s' has expired", code));
            }
        } catch (NoResultException e) {
            msg = new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "Error",
                "Registration code is invalid. " +
                        "Try visiting registration page and sending another registration link. " +
                        "If error persists contact administrator. ");
            log.error(String.format("registration key code '%s' not found: %s", code, e.getMessage()));
        }

        context.addMessage(null, msg);
    }

    @Transactional
    public void changePw() {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        try {
            final ChangePwKey changePwKey = changePwKeyDAO.selectByCode(code);
            final User user = changePwKey.getUser();

            //TODO: pakeisti į hash'intą password'ą
//            final String password = pwHasher.hash(((Password)context.getViewRoot().findComponent("complete-change-pw-form:new-password")).getValue().toString());
            final String newPassword = ((Password)context.getViewRoot().findComponent("complete-change-pw-form:new-password")).getValue().toString();
            user.setPassword(newPassword);

            changePwKeyDAO.delete(changePwKey);
            changePwKeyDAO.deleteByUserId(user.getId());
            userDAO.update(user);

            msg = new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Success",
                    "Successfully changed password");
            log.info(String.format("Successfully changed password with key code '%s' for user with email '%s' successful", code, user.getEmail()));
        } catch (NoResultException e) {
            msg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "Change password code is invalid. " +
                            "Try visiting change password page and sending another link. " +
                            "If error persists contact administrator. ");
            log.error(String.format("Change password key code '%s' not found: %s", code, e.getMessage()));
        }

        context.addMessage(null, msg);
    }

}
