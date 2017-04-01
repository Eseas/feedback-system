package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.entities.PotentialUser;
import lt.vu.feedback_system.entities.User;
import lt.vu.feedback_system.dao.PotentialUserDAO;
import lt.vu.feedback_system.dao.UserDAO;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
@Slf4j
public class RequestUsersController {
    @Getter
    private PotentialUser potentialUser = new PotentialUser();
    @Getter
    private PotentialUser potentialUserToRemove = new PotentialUser();

    @Inject
    private UserDAO userDAO;
    @Inject
    private PotentialUserDAO potentialUserDAO;

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<PotentialUser> getAllPotentialUsers() {
        return potentialUserDAO.getAllPotentialUsers();
    }

    @Transactional
    public void createPotentialUser() {
        potentialUserDAO.create(potentialUser);
        potentialUser = new PotentialUser();
    }

    @Transactional
    public void removePotentialUser() {
        List<PotentialUser> potentialUsers = potentialUserDAO.getAllPotentialUsers();
        for (PotentialUser potentialUser : potentialUsers) {
            if (potentialUser.equals(potentialUserToRemove)) {
                potentialUserDAO.delete(potentialUser);
                break;
            }
        }
        potentialUserToRemove = new PotentialUser();
    }
}
