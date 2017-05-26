package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.businesslogic.users.UserLogic;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.User;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UserManagementController implements Serializable {
    @Getter
    private User user = new User();

    @Inject
    private UserDAO userDAO;

    @Inject
    private UserLogic userLogic;

    @Inject
    private UserContext userContext;

    @Getter private User selectedUser;
    @Getter private List<User> users;

    @PostConstruct
    public void loadData() {
        users = userDAO.getAllUsers();
    }

    public void prepareForEditing(User user) {
        selectedUser = user;
    }

    @Transactional
    public String updateSelectedUser() {
        try {
            userDAO.updateAndFlush(selectedUser);

            if (userContext.getUser().getId().equals(selectedUser.getId())) {
                FacesContext context = FacesContext.getCurrentInstance();
                return context.getViewRoot().getViewId() + "?faces-redirect=true";
            } else {
                reloadAll();
            }
        } catch (OptimisticLockException ole) {
            selectedUser = userDAO.getUserById(selectedUser.getId());
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);

            FacesMessage msg = new FacesMessage("Someone has updated this user!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("edit_user_msg", msg);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getViewRoot().getViewId();
    }

    public void reloadAll() {
        users = userDAO.getAllUsers();
    }
}