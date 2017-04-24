package lt.vu.feedback_system.utils.security;


public interface PasswordHasher {

    String hash(String pw);

    boolean check(String candidatePw, String hashedPw);

}
