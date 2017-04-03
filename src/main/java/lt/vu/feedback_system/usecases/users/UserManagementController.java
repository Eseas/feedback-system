package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.dao.UserDAO;
import lt.vu.feedback_system.entities.User;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;


@Model
@Slf4j
public class UserManagementController {
    @Getter
    private User user = new User();
    @Inject
    private UserDAO userDAO;

    @Transactional
    public void setAdmin(boolean current_state) {
        try {
            user = userDAO.getUserByEmail(user.getEmail());
            user.setAdmin(current_state);
        }
        catch (javax.persistence.NoResultException ex) {
            Messages.addGlobalError("Wrong email");
        }
    }
    @Transactional
    public void setBlock(boolean current_state) {
        try {
            user = userDAO.getUserByEmail(user.getEmail());
            user.setBlocked(current_state);
        }
        catch (javax.persistence.NoResultException ex) {
            Messages.addGlobalError("Wrong email");
        }
    }
}
