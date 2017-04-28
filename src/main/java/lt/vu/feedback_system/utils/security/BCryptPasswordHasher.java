package lt.vu.feedback_system.utils.security;

import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BCryptPasswordHasher implements PasswordHasher {

    public String hash(String pw) {
        return BCrypt.hashpw(pw, BCrypt.gensalt());
    }

    public boolean check(String candidatePw, String hashedPw) {
        return BCrypt.checkpw(candidatePw, hashedPw);
    }

}
