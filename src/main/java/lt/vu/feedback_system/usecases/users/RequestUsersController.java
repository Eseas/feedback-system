package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.config.Configuration;
import lt.vu.feedback_system.dao.RegKeyDAO;
import lt.vu.feedback_system.entities.PotentialUser;
import lt.vu.feedback_system.entities.RegKey;
import lt.vu.feedback_system.entities.User;
import lt.vu.feedback_system.dao.PotentialUserDAO;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.mailing.MailContent;
import lt.vu.feedback_system.mailing.Mailer;
import lt.vu.feedback_system.utils.HexStringGen;
import lt.vu.feedback_system.utils.ParserWithDefaults;
import org.apache.commons.mail.EmailException;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Synchronization;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Model
@Slf4j
public class RequestUsersController {

    @Getter
    private PotentialUser potentialUser = new PotentialUser();

    @Getter
    private PotentialUser potentialUserToRemove = new PotentialUser();

    @Getter
    private List<User> users ;

    @Getter
    private User user;

    @Inject
    private UserDAO userDAO;

    @Inject
    private PotentialUserDAO potentialUserDAO;

    @Inject
    private RegKeyDAO regKeyDAO;

    @Inject
    private Configuration config;

    @Inject
    private Mailer mailer;

    @PostConstruct
    public void loadData() {
        users = userDAO.getAllUsers();
    }

    public List<PotentialUser> getAllPotentialUsers() {
        return potentialUserDAO.getAllPotentialUsers();
    }

    @Transactional
    public void createPotentialUser() {
        potentialUserDAO.create(potentialUser);
        potentialUser = new PotentialUser();
    }

    @Transactional
    public void removePotentialUser() {
        List<PotentialUser> potentialUsers = potentialUserDAO.getAllPotentialUsers();
        for (PotentialUser potentialUser : potentialUsers) {
            if (potentialUser.equals(potentialUserToRemove)) {
                potentialUserDAO.delete(potentialUser);
                break;
            }
        }
        potentialUserToRemove = new PotentialUser();
    }

    @Transactional
    public void update(){
        for (User user: users) {
            userDAO.merge(user);
        }
    }

    public void sendRegLink(final String email) {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;

        try {
            sendRegLinkUnhandled(email);
            msg = new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Success",
                    String.format("Registration link sent to '%s'", email));
            log.info(String.format("registration link sent to '%s'", email));
        } catch (EmailException e) {
            msg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Error!",
                    String.format("Failed to send registration link to '%s'", email));
            log.error(String.format("failed to send registration link to '%s': %s", email, e.getMessage()));
        }

        context.addMessage("potential-users", msg);
    }

    @Transactional(rollbackOn = EmailException.class)
    private void sendRegLinkUnhandled(final String email) throws EmailException {
        final int expirationInHours = ParserWithDefaults.parseInt(config.getProps().getProperty("reg.expirationInHours"), 24);
        final LocalDateTime expires = LocalDateTime.now().plusHours(expirationInHours);
        final String code = HexStringGen.getHexString();

        final RegKey key = new RegKey();
        key.setExpires(expires);
        key.setCode(code);
        regKeyDAO.create(key);

        final String url = String.format("%s/%s", config.getProps().getProperty("baseUrl"), "register.html");
        final String msg = String.format("Use this link: %s to register. Link is valid until %s", url, expires);
        final MailContent content = new MailContent(Collections.singletonList(email), "Registration to the FeedbackSystem", msg);
        mailer.sendEmail(content);
    }

}
