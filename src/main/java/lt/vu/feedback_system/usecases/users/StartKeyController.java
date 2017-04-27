package lt.vu.feedback_system.usecases.users;

import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.config.Configuration;
import lt.vu.feedback_system.dao.ChangePwKeyDAO;
import lt.vu.feedback_system.dao.PotentialUserDAO;
import lt.vu.feedback_system.dao.RegKeyDAO;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.ChangePwKey;
import lt.vu.feedback_system.entities.PotentialUser;
import lt.vu.feedback_system.entities.RegKey;
import lt.vu.feedback_system.entities.User;
import lt.vu.feedback_system.mailing.MailContent;
import lt.vu.feedback_system.mailing.Mailer;
import lt.vu.feedback_system.utils.HexStringGen;
import lt.vu.feedback_system.utils.ParserWithDefaults;
import org.apache.commons.mail.EmailException;
import org.primefaces.component.inputtext.InputText;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Slf4j
@RequestScoped
@Named("startKeyController")
public class StartKeyController {

    private RegKeyDAO regKeyDAO;

    private ChangePwKeyDAO changePwKeyDAO;

    private PotentialUserDAO potentialUserDAO;

    private UserDAO userDAO;

    private Configuration config;

    private Mailer mailer;

    protected StartKeyController() {}

    @Inject
    public StartKeyController(RegKeyDAO regKeyDAO,
                              ChangePwKeyDAO changePwKeyDAO,
                              PotentialUserDAO potentialUserDAO,
                              UserDAO userDAO,
                              Configuration config,
                              Mailer mailer) {
        this.regKeyDAO = regKeyDAO;
        this.changePwKeyDAO = changePwKeyDAO;
        this.potentialUserDAO = potentialUserDAO;
        this.userDAO = userDAO;
        this.config = config;
        this.mailer = mailer;
    }

    @Transactional
    public void sendRegLink() {
        final FacesContext context = FacesContext.getCurrentInstance();
        final String email = ((InputText)context.getViewRoot().findComponent("start-reg-form:email")).getValue().toString();
        FacesMessage facesMsg;

        try {
            final PotentialUser user = potentialUserDAO.selectByEmail(email);
            sendEmailWithRegLink(user);
            facesMsg = new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Success",
                    String.format("Registration link sent to '%s'. Check your email and proceed with the registration.", email));
        } catch (NoResultException e) {
            log.error(String.format("Failed to send registration link to '%s': %s", email, e.getMessage()));
            facesMsg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Error",
                    String.format("Email '%s' is not registered, please contact system administrator.", email));
        } catch (EmailException e) {
            log.error(String.format("Failed to send registration link to '%s': %s", email, e.getMessage()));
            facesMsg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Error",
                    String.format("Failed to send registration link to '%s'. " +
                            "Try again later. " +
                            "If error persists contact system administrator", email));
        }

        context.addMessage(null, facesMsg);
    }

    @Transactional
    public void sendChangePwLink() {
        final FacesContext context = FacesContext.getCurrentInstance();
        final String email = ((InputText)context.getViewRoot().findComponent("start-change-pw-form:email")).getValue().toString();
        FacesMessage facesMsg;

        try {
            final User user = userDAO.getUserByEmail(email);
            sendEmailWithChangePwLink(user);
            facesMsg = new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Success",
                    String.format("Link sent to '%s'. Check your email and proceed with changing your password.", email));
        } catch (NoResultException e) {
            log.error(String.format("Failed to send change password link to '%s': %s", email, e.getMessage()));
            facesMsg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Error",
                    String.format("User with email '%s' does not exist, please contact system administrator.", email));
        } catch (EmailException e) {
            log.error(String.format("Failed to send change passowrd link link to '%s': %s", email, e.getMessage()));
            facesMsg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Error",
                    String.format("Failed to send change password link to '%s'. " +
                            "Try again later. " +
                            "If error persists contact system administrator", email));
        }

        context.addMessage(null, facesMsg);
    }

    private void sendEmailWithRegLink(final PotentialUser user) throws EmailException {
        final String code = HexStringGen.getHexString();
        final int expirationInHours = ParserWithDefaults.parseInt(config.getProps().getProperty("reg.expirationInHours"), 24);
        final LocalDateTime expires = LocalDateTime.now().plusHours(expirationInHours);
        expires.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        final RegKey key = new RegKey(code, expires, user);
        regKeyDAO.create(key);

        final String baseUrl = config.getProps().getProperty("baseUrl");
        final String url = String.format("%s/%s?code=%s", baseUrl, "complete-reg.html", key.getCode());
        final String msg = String.format("Use this link: %s to register. Link is valid until %s", url, expires);
        final MailContent content = new MailContent(Collections.singletonList(key.getUser().getEmail()),
                                            "Registration to the FeedbackSystem", msg);
        mailer.sendEmail(content);
    }

    private void sendEmailWithChangePwLink(final User user) throws EmailException {
        final String code = HexStringGen.getHexString();

        final ChangePwKey key = new ChangePwKey(code, user);
        changePwKeyDAO.create(key);

        final String baseUrl = config.getProps().getProperty("baseUrl");
        final String url = String.format("%s/%s?code=%s", baseUrl, "complete-change-pw.html", key.getCode());
        final String msg = String.format("Use this link: %s to change password.", url);
        final MailContent content = new MailContent(Collections.singletonList(key.getUser().getEmail()),
                "Change password in FeedbackSystem", msg);
        mailer.sendEmail(content);
    }

}
