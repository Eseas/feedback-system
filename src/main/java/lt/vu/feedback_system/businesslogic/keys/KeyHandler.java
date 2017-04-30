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
import lt.vu.feedback_system.mailing.MailContent;
import lt.vu.feedback_system.mailing.Mailer;
import lt.vu.feedback_system.utils.HexStringGen;
import lt.vu.feedback_system.utils.ParserWithDefaults;
import lt.vu.feedback_system.utils.security.PasswordHasher;
import org.apache.commons.mail.EmailException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Properties;

@ApplicationScoped
public class KeyHandler {

    private String systemName;

    private String baseURL;

    private int expirationInHours;

    private RegKeyDAO regKeyDAO;

    private ChangePwKeyDAO changePwKeyDAO;

    private PotentialUserDAO potentialUserDAO;

    private UserDAO userDAO;

    private Mailer mailer;

    private PasswordHasher pwHasher;

    protected KeyHandler() {}

    @Inject
    public KeyHandler(Configuration config,
                      RegKeyDAO regKeyDAO,
                      ChangePwKeyDAO changePwKeyDAO,
                      PotentialUserDAO potentialUserDAO,
                      UserDAO userDAO,
                      Mailer mailer,
                      PasswordHasher pwHasher) {
        final Properties props = config.getProps();
        this.systemName = props.getProperty("common.systemName");
        this.baseURL = props.getProperty("common.baseURL");
        this.expirationInHours = ParserWithDefaults.parseInt(props.getProperty("reg.expirationInHours"), 48);
        this.regKeyDAO = regKeyDAO;
        this.changePwKeyDAO = changePwKeyDAO;
        this.potentialUserDAO = potentialUserDAO;
        this.userDAO = userDAO;
        this.mailer = mailer;
        this.pwHasher = pwHasher;
    }

    private String formLink(final String page, final String code) {
        return String.format("%s/%s?code=%s", baseURL, page, code);
    }

    private void sendRegLink(final String email, final String code, final LocalDateTime expires) throws EmailException {
        final String link = formLink("complete-reg.html", code);
        final String expFormatted = expires.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        final String msg = String.format("Use this link %s to register. The link if valid until %s", link, expFormatted);
        final String subject = String.format("Registration to %s", systemName);
        mailer.sendEmail(new MailContent(Collections.singletonList(email), subject, msg));
    }

    private void sendChangePwLink(final String email, final String code) throws EmailException {
        final String link = formLink("complete-change-pw.html", code);
        final String msg = String.format("Use this link %s to change password.", link);
        final String subject = String.format("Change password in %s", systemName);
        mailer.sendEmail(new MailContent(Collections.singletonList(email), subject, msg));
    }

    @Transactional(rollbackOn = EmailException.class)
    public void startReg(final String email) throws EmailException {
        final PotentialUser user = potentialUserDAO.selectByEmail(email);
        final RegKey key = new RegKey(HexStringGen.getHexString(), LocalDateTime.now().plusHours(expirationInHours), user);
        regKeyDAO.create(key);
        sendRegLink(user.getEmail(), key.getCode(), key.getExpires());
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
        if (regKey.getExpires().compareTo(LocalDateTime.now()) == 1) {
            final PotentialUser potentialUser = regKey.getUser();
            //TODO: use hashed version instead
//            final User user = new User(firstName, lastName, potentialUser.getEmail(), pwHasher.hash(password), false, false);
            final User user = new User(firstName, lastName, potentialUser.getEmail(), password, false, false);
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
        //TODO: use hashed version instead
//        user.setPassword(pwHasher.hash(newPassword));
        user.setPassword(newPassword);
        changePwKeyDAO.deleteByUserId(user.getId());
        userDAO.update(user);
    }

}
