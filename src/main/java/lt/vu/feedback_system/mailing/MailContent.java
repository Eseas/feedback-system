package lt.vu.feedback_system.mailing;

import lombok.Getter;
import java.util.List;


//TODO: html formatted emails with attachments
@Getter
public class MailContent {

    private final List<String> recipientsEmails;

    private final String subject;

    private final String msg;

    public MailContent(final List<String> recipientsEmails, final String subject, final String msg) {
        this.recipientsEmails = recipientsEmails;
        this.subject = subject;
        this.msg = msg;
    }

}
