package lt.vu.feedback_system.utils.email;

import lt.vu.feedback_system.utils.ParserWithDefaults;
import org.apache.commons.mail.*;
import java.util.Properties;


public final class ConfiguredEmailFactory {

    private ConfiguredEmailFactory() { }

    private static Email configure(final Properties props, final Email email) throws EmailException {
        final String hostname = props.getProperty("email.hostname");
        final int port = ParserWithDefaults.parseInt(props.getProperty("email.port"));
        final String sender = props.getProperty("email.sender");
        final String senderEmail = props.getProperty("email.senderEmail");
        final String password = props.getProperty("email.password");
        email.setHostName(hostname);
        email.setSmtpPort(port);
        email.setAuthenticator(new DefaultAuthenticator(sender, password));
        email.setFrom(senderEmail);
        email.setSSLOnConnect(true);
        email.addTo(senderEmail, "Undisclosed recipient");
        return email;
    }

    public static SimpleEmail getSimpleEmail(final Properties props) throws EmailException { return (SimpleEmail) configure(props, new SimpleEmail()); }

    public static HtmlEmail getHtmlEmail(final Properties props) throws EmailException { return (HtmlEmail) configure(props, new HtmlEmail()); }

}
