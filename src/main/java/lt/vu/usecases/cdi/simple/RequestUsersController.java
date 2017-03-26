package lt.vu.usecases.cdi.simple;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.dao.PotentialUserDAO;
import lt.vu.dao.UserDAO;
import lt.vu.entities.PotentialUser;
import lt.vu.entities.User;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Model // tas pats kaip: @Named ir @RequestScoped
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
//
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        return users;
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
