package lt.vu.feedback_system.businesslogic.generate;

import lt.vu.feedback_system.utils.generate.Hash;
import lt.vu.feedback_system.utils.generate.HashGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.Random;

@Default
@Hash
@ApplicationScoped
public class StringHashGenerator implements HashGenerator {

    @Override
    public String hash(String data) {
        return randomString(10);
    }

    private String randomString(int length) {
        final String symbols =
                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final Random rand = new Random();
        final StringBuilder output = new StringBuilder(length);

        for(int i = 0; i < length; i++) {
            output.append(symbols.charAt(rand.nextInt(symbols.length())));
        }

        return output.toString();
    }
}
