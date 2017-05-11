package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.businesslogic.keys.KeyExpiredException;
import lt.vu.feedback_system.businesslogic.keys.KeyHandler;
import lt.vu.feedback_system.businesslogic.keys.UserRegisteredException;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.NoResultException;

@Slf4j
@Model
public class KeyController {

    private FacesContext context;

    private KeyHandler keyHandler;

    @Getter
    @Setter
    private String code;

    protected KeyController() {}

    @Inject
    public KeyController(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        this.context = FacesContext.getCurrentInstance();
    }

    /**
     * @param option indicate for what use case email is sent. 0 => send registration link, other => send change password link
     */
    public void sendEmail(final int option) {
        final String email = ((InputText)context.getViewRoot().findComponent("send-email-form:email")).getValue().toString();
        FacesMessage msg;

        try {
            if (option == 0) keyHandler.startReg(email);
            else keyHandler.startChangePw(email);

            msg = new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Success",
                    String.format("Link sent to '%s'. Check your email and proceed with instructions.", email));
            log.info(String.format("Link sent to '%s'", email));
        } catch (Throwable e) {
            final Throwable cause = e.getCause();
            if (cause instanceof NoResultException) {
                msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Error",
                        String.format("Email '%s' is not registered, please contact system administrator.", email));
                log.info(String.format("Failed to send link to '%s'. Email is not registered.", email));
            } else if (cause instanceof UserRegisteredException) {
                msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Error",
                        String.format("User with email '%s' is already registered.", email));
                log.info(String.format("User with email '%s' is already registered.", email));
            } else {
                msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Error",
                        String.format("Failed to send link to '%s'. " +
                                "Try again later. " +
                                "If error persists contact system administrator.", email));
                log.error(String.format("Failed to send link to '%s': %s", email, e.getMessage()));
            }
        }

        context.addMessage(null, msg);
    }

    public void completeRegister() {
        final String firstName = ((InputText)context.getViewRoot().findComponent("complete-reg-form:first-name")).getValue().toString();
        final String lastName = ((InputText)context.getViewRoot().findComponent("complete-reg-form:last-name")).getValue().toString();
        final String password = ((Password)context.getViewRoot().findComponent("complete-reg-form:password")).getValue().toString();
        FacesMessage msg;

        try {
            keyHandler.completeReg(code, firstName, lastName, password);
            msg = new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Success",
                    "New user successfully created visit Index to login.");
            log.info(String.format("User '%s %s' created", firstName, lastName));
        } catch (Throwable e) {
            final Throwable cause = e.getCause();
            if (cause instanceof KeyExpiredException) {
                msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Error",
                        "Registration code has expired. " +
                                "Visit registration page and send another registration link. ");
            } else if (cause instanceof NoResultException) {
                msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Error",
                        "Registration code is invalid. " +
                                "Try visiting registration page and sending another registration link. " +
                                "If error persists contact administrator. ");
                log.info(String.format("Registration key code '%s' not found.", code));
            } else {
                msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Error",
                        "Failed to register. " +
                                "Try visiting registration page and sending another registration link. " +
                                "If error persists contact administrator. ");
                log.error(String.format("Failed to register '%s %s'. Code %s. %s", firstName, lastName, code, e.getMessage()));
            }
        }

        context.addMessage(null, msg);
    }

    public void completeChangePw() {
        final String newPassword = ((Password)context.getViewRoot()
                .findComponent("complete-change-pw-form:new-password")).getValue().toString();
        FacesMessage msg;

        try {
            keyHandler.completeChangePw(code, newPassword);
            msg = new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Success",
                    "Successfully changed password");
            log.info(String.format("Successfully changed password with key code '%s'.", code));
        } catch (Throwable e) {
            if (e.getCause() instanceof NoResultException) {
                msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Error",
                        "Change password code is invalid. " +
                                "Try visiting change password page and sending another link. " +
                                "If error persists contact administrator. ");
                log.info(String.format("Change password key code '%s' not found.", code));
            } else {
                msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Error",
                        "Failed to change password. " +
                                "Try visiting registration page and sending another link for password change. " +
                                "If error persists contact administrator. ");
                log.error(String.format("Failed to change password with code %s. %s", code, e.getMessage()));
            }
        }

        context.addMessage(null, msg);
    }

}
