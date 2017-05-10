package lt.vu.feedback_system.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    public static String encodeRedirect(String requestUri, String redirectUri, String params)
            throws UnsupportedEncodingException {
        StringBuilder redirectUrl = new StringBuilder();
        String encodedUrl = new String();

        redirectUrl.append(requestUri);

        if (!(redirectUri.compareToIgnoreCase("/") == 0 || redirectUri.length() == 0)) {
            redirectUrl.append("?redirect=");
            encodedUrl += redirectUri;
        }

        if (params != null) {
            encodedUrl += "?" + params;
        }

        encodedUrl = URLEncoder.encode(encodedUrl, "UTF-8");
        redirectUrl.append(encodedUrl);

        return redirectUrl.toString();
    }
}
