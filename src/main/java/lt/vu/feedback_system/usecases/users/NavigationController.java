package lt.vu.feedback_system.usecases.users;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class NavigationController {
    /**
     * Redirect to login page.
     * @return Login page name.
     */
    public String redirectToLogin() {
        return "/login.xhtml?faces-redirect=true";
    }

    /**
     * Go to login page.
     * @return Login page name.
     */
    public String toLogin() {
        return "/login.xhtml";
    }

    /**
     * Redirect to index page.
     * @return Index page name.
     */
    public String redirectToIndex() {
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * Go to index page.
     * @return Index page name.
     */
    public String toIndex() {
        return "/index.xhtml";
    }
}
