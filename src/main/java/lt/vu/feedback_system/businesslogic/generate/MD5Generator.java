package lt.vu.feedback_system.businesslogic.generate;

import lt.vu.feedback_system.utils.generate.Hash;
import lt.vu.feedback_system.utils.generate.HashGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Default
@Hash
@ApplicationScoped
public class MD5Generator implements HashGenerator {

    @Override
    public String hash(String data) {
        try {
            return generateHash(data);
        } catch (NoSuchAlgorithmException ex) {}

        return "";
    }

    public static String generateHash(String input) throws NoSuchAlgorithmException {
        String md5 = null;

        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update(input.getBytes(), 0, input.length());
        md5 = new java.math.BigInteger(1, digest.digest()).toString(16);

        return md5;
    }
}
