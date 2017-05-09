package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.dao.RegKeyDAO;
import lt.vu.feedback_system.entities.PotentialUser;
import lt.vu.feedback_system.entities.User;
import lt.vu.feedback_system.dao.PotentialUserDAO;
import lt.vu.feedback_system.dao.UserDAO;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.*;

@Model
@Slf4j
public class RequestUsersController {

    @Getter
    private PotentialUser potentialUser = new PotentialUser();

    @Getter
    private PotentialUser potentialUserToRemove = new PotentialUser();

    @Getter
    private List<User> users ;

    @Inject
    private UserDAO userDAO;

    @Inject
    private PotentialUserDAO potentialUserDAO;

    @Inject
    private RegKeyDAO regKeyDAO;

    @PostConstruct
    public void loadData() {
        users = userDAO.getAllUsers();
    }

    public List<PotentialUser> getAllPotentialUsers() {
        return potentialUserDAO.getAllPotentialUsers();
    }

    @Transactional
    public void createPotentialUser() {
        try{
            potentialUserDAO.selectByEmail(potentialUser.getEmail());
        }
        catch(NoResultException e){
            potentialUserDAO.create(potentialUser);
            potentialUser = new PotentialUser();
        }
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

    @Transactional
    public void removePotentialUser(PotentialUser potentialUserToRemove) {
        List<PotentialUser> potentialUsers = potentialUserDAO.getAllPotentialUsers();
        for (PotentialUser potentialUser : potentialUsers) {
            if (potentialUser.equals(potentialUserToRemove)) {
                potentialUserDAO.delete(potentialUser);
                break;
            }
        }
    }

    @Transactional
    public void update(){
        for (User user: users) {
            userDAO.update(user);
        }
    }

}
