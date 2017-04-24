package lt.vu.feedback_system.utils;

import java.util.UUID;

public final class HexStringGen {

    private HexStringGen() {}

    /**
     * @return hex string 32 characters long
     */
    public static String getHexString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @param length returned hex string length. If greater than 32, default string of 32 characters will be returned.
     * @return hex string
     */
    public static String getHexString(final int length) {
        final String hexString = getHexString();
        return length <= hexString.length() ? getHexString().substring(0, length) : hexString;
    }

}
