package lt.vu.feedback_system.businesslogic.generate;

import lt.vu.feedback_system.utils.generate.PasswordHash;
import lt.vu.feedback_system.utils.generate.PasswordHasher;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

@Default
@PasswordHash
@ApplicationScoped
public class BCryptPasswordHasher implements PasswordHasher {

    public String hash(String pw) {
        return BCrypt.hashpw(pw, BCrypt.gensalt());
    }

    public boolean check(String candidatePw, String hashedPw) {
        return BCrypt.checkpw(candidatePw, hashedPw);
    }

}
