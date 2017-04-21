package lt.vu.feedback_system.mailing;

import lt.vu.feedback_system.config.Configuration;
import lt.vu.feedback_system.utils.ParserWithDefaults;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Properties;


@ApplicationScoped
public class Mailer {

    private final Configuration config;

    protected Mailer() { this.config = null; }

    @Inject
    private Mailer(Configuration config) { this.config = config; }

    public void sendEmail(final MailContent content) throws EmailException {
        final Properties props = config.getProps();
        final String hostname = props.getProperty("email.hostname");
        final int port = ParserWithDefaults.parseInt(props.getProperty("email.port"));
        final String sender = props.getProperty("email.sender");
        final String senderEmail = props.getProperty("email.senderEmail");
        final String password = props.getProperty("email.password");
        final Email email = new SimpleEmail();

        email.setHostName(hostname);
        email.setSmtpPort(port);
        email.setAuthenticator(new DefaultAuthenticator(sender, password));
        email.setSSLOnConnect(true);
        email.setFrom(senderEmail);
        email.setSubject(content.getSubject());
        email.setMsg(content.getMsg());

        email.addTo(senderEmail, "Undisclosed recipient");
        for (String r : content.getRecipientsEmails()) email.addBcc(r);

        email.send();
    }

}
