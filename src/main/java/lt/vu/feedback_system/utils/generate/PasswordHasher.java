package lt.vu.feedback_system.utils.generate;

public interface PasswordHasher extends HashGenerator {

    boolean check(String candidatePw, String hashedPw);

}
