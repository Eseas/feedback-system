package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lombok.Setter;
import lt.vu.feedback_system.businesslogic.users.UserContext;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Model
public class LoginController {

    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private String redirectUrl;

    @Inject
    private UserContext userContext;

    @Inject
    private NavigationBean navigationBean;

    public String doLogin() {
        try {
            userContext.login(email, password);

            if (userContext.isLoggedIn()) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("userContext", userContext);

                if (redirectUrl != null && !redirectUrl.isEmpty()) {
                    return navigationBean.redirectTo(redirectUrl);
                } else {
                    return navigationBean.redirectToIndex();
                }
            } else {
                FacesMessage msg = new FacesMessage("Wrong email or password!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);

                return navigationBean.toLogin();
            }
        } catch (IllegalAccessException ex) {
            FacesMessage msg = new FacesMessage("This user is blocked! Please contact administrator.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);

            return navigationBean.toLogin();
        }
    }

    public String doLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        userContext.logout();

        return navigationBean.redirectToIndex();
    }
}
