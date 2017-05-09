package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lt.vu.feedback_system.businesslogic.users.UserLogic;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.User;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
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

    @Getter
    private List<User> users;

    @Inject
    private UserDAO userDAO;

    @Inject
    private UserLogic userLogic;

    @Getter private User selectedUser;
    @Getter private User conflictingUser;
    @Getter private List<User> allUsers;

    @PostConstruct
    public void loadData() {
        users = userDAO.getAllUsers();
    }

    public void prepareForEditing(User user) {
        selectedUser = user;
        conflictingUser = null;
    }

    @Transactional
    public void updateSelectedUser() {
        try {
            userDAO.updateAndFlush(selectedUser);
            reloadAll();
        } catch (OptimisticLockException ole) {
            conflictingUser = userDAO.getUserById(selectedUser.getId());
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
        }
    }

    @Transactional
    public void overwriteUser() {
        selectedUser.setOptLockVersion(conflictingUser.getOptLockVersion());
        updateSelectedUser();
    }

    public void reloadAll() {
        allUsers = userDAO.getAllUsers();
    }
}