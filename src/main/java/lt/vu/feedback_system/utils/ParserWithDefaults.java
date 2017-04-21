package lt.vu.feedback_system.utils;

public final class ParserWithDefaults {

    private ParserWithDefaults() {}

    public static int parseInt(final String toParse, final int defaultVal) {
        try {
            return Integer.parseInt(toParse);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static int parseInt(final String toParse) { return parseInt(toParse, 0); }

}
