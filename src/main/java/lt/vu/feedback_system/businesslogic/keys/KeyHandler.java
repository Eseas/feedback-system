package lt.vu.feedback_system.businesslogic.keys;

import lt.vu.feedback_system.config.Configuration;
import lt.vu.feedback_system.dao.ChangePwKeyDAO;
import lt.vu.feedback_system.dao.PotentialUserDAO;
import lt.vu.feedback_system.dao.RegKeyDAO;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.ChangePwKey;
import lt.vu.feedback_system.entities.PotentialUser;
import lt.vu.feedback_system.entities.RegKey;
import lt.vu.feedback_system.entities.User;
import lt.vu.feedback_system.utils.email.ConfiguredEmailFactory;
import lt.vu.feedback_system.utils.HexStringGen;
import lt.vu.feedback_system.utils.ParserWithDefaults;
import lt.vu.feedback_system.utils.security.PasswordHasher;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@ApplicationScoped
public class KeyHandler {

    private Properties props;

    private int expirationInHours;

    private RegKeyDAO regKeyDAO;

    private ChangePwKeyDAO changePwKeyDAO;

    private PotentialUserDAO potentialUserDAO;

    private UserDAO userDAO;

    private PasswordHasher pwHasher;

    protected KeyHandler() {}

    @Inject
    public KeyHandler(Configuration config,
                      RegKeyDAO regKeyDAO,
                      ChangePwKeyDAO changePwKeyDAO,
                      PotentialUserDAO potentialUserDAO,
                      UserDAO userDAO,
                      PasswordHasher pwHasher) {
        this.props = config.getProps();
        this.expirationInHours = ParserWithDefaults.parseInt(props.getProperty("reg.expirationInHours"), 48);
        this.regKeyDAO = regKeyDAO;
        this.changePwKeyDAO = changePwKeyDAO;
        this.potentialUserDAO = potentialUserDAO;
        this.userDAO = userDAO;
        this.pwHasher = pwHasher;
    }

    private String formLink(final String page, final String code) {
        return String.format("%s/%s?code=%s", props.getProperty("common.baseURL"), page, code);
    }

    private void sendRegLink(final String emailAddress, final String code, final LocalDateTime expires) throws EmailException {
        final String link = formLink("complete-reg.html", code);
        final String expFormatted = expires.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        final String htmlMsg = String.format("<p>Use this <a href=\"%s\">link</a> to register. The link if valid until %s.</p>", link, expFormatted);
        final String msg = String.format("Use this link %s to register. The link is valid until %s.", link, expFormatted);
        final String subject = String.format("Registration to %s", props.getProperty("common.systemName"));

        final HtmlEmail email = ConfiguredEmailFactory.getHtmlEmail(props);
        email.setSubject(subject);
        email.addBcc(emailAddress);
        email.setTextMsg(msg);
        email.setHtmlMsg(htmlMsg);
        email.send();
    }

    private void sendChangePwLink(final String emailAddress, final String code) throws EmailException {
        final String link = formLink("complete-change-pw.html", code);
        final String htmlMsg = String.format("<p>Use this <a href=\"%s\">link</a> to change password.</p>", link);
        final String msg = String.format("Use this link %s to change password.", link);
        final String subject = String.format("Change password in %s", props.getProperty("common.systemName"));

        final HtmlEmail email = ConfiguredEmailFactory.getHtmlEmail(props);
        email.setSubject(subject);
        email.addBcc(emailAddress);
        email.setTextMsg(msg);
        email.setHtmlMsg(htmlMsg);
        email.send();
    }

    @Transactional(rollbackOn = EmailException.class)
    public void startReg(final String email) throws EmailException, UserRegisteredException {
        if (!userDAO.userExists(email)) {
            final PotentialUser user = potentialUserDAO.selectByEmail(email);
            final RegKey key = new RegKey(HexStringGen.getHexString(), LocalDateTime.now().plusHours(expirationInHours), user);
            regKeyDAO.create(key);
            sendRegLink(user.getEmail(), key.getCode(), key.getExpires());
        } else throw new UserRegisteredException(String.format("User with email '%s' is already registered", email));
    }

    @Transactional(rollbackOn = EmailException.class)
    public void startChangePw(final String email) throws EmailException {
        final User user = userDAO.getUserByEmail(email);
        final ChangePwKey key = new ChangePwKey(HexStringGen.getHexString(), user);
        changePwKeyDAO.create(key);
        sendChangePwLink(user.getEmail(), key.getCode());
    }

    @Transactional(dontRollbackOn = KeyExpiredException.class)
    public void completeReg(final String code, final String firstName, final String lastName, final String password) throws KeyExpiredException {
        final RegKey regKey = regKeyDAO.selectByCode(code);
        if (regKey.getExpires().compareTo(LocalDateTime.now()) > 0) {
            final PotentialUser potentialUser = regKey.getUser();
            final User user = new User(firstName, lastName, potentialUser.getEmail(), pwHasher.hash(password), false, false);
            regKeyDAO.deleteByUserId(potentialUser.getId());
            potentialUserDAO.delete(potentialUser);
            userDAO.create(user);
        } else {
            regKeyDAO.delete(regKey);
            final String expFormatted = regKey.getExpires().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
            throw new KeyExpiredException(String.format("Registration key expired on %s", expFormatted));
        }
    }

    @Transactional
    public void completeChangePw(final String code, final String newPassword) {
        final ChangePwKey changePwKey = changePwKeyDAO.selectByCode(code);
        final User user = changePwKey.getUser();
        user.setPassword(pwHasher.hash(newPassword));
        changePwKeyDAO.deleteByUserId(user.getId());
        userDAO.update(user);
    }

}
