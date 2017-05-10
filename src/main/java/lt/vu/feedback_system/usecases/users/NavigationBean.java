package lt.vu.feedback_system.usecases.users;

import lt.vu.feedback_system.utils.FacesUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class NavigationBean {

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

    /**
     * Redirect to a page with faces redirect.
     * @return Page to redirect with faces redirect.
     */
    public String redirectTo(String redirectUrl) {
        return FacesUtil.redirectTo(redirectUrl);
    }

    public String toMySurveys() {
        return redirectTo("/surveys.html?admin-mode=false");
//        return "/surveys.html?admin-mode=false";
    }
}
