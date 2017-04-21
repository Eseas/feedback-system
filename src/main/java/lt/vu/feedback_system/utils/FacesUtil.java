package lt.vu.feedback_system.utils;

public class FacesUtil {

    private FacesUtil() {}

    public static String redirectTo(String redirectUrl) {
        if (redirectUrl.contains("?")) {
            redirectUrl += "&faces-redirect=true";
        } else {
            redirectUrl += "?faces-redirect=true";
        }

        return redirectUrl;
    }
}
