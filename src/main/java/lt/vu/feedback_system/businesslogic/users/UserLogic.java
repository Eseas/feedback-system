package lt.vu.feedback_system.businesslogic.users;

import lt.vu.feedback_system.dao.PotentialUserDAO;
import lt.vu.feedback_system.entities.PotentialUser;
import org.apache.commons.validator.routines.EmailValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserLogic {
    @Inject
    private PotentialUserDAO potentialUserDAO;

    @Transactional
    public void createPotentialUser(PotentialUser potentialUser) {
        potentialUserDAO.create(potentialUser);
    }

    @Transactional
    public void removePotentialUser(PotentialUser potentialUser) {
        potentialUserDAO.delete(potentialUser);
    }

    public boolean isEmailFree(String email) {
        try{
            potentialUserDAO.selectByEmail(email);
            return false;
        }
        catch (NoResultException nre) {
            return true;
        }
    }

    public boolean isEmailFormatValid(String email) {
        if(EmailValidator.getInstance().isValid(email))
            return true;
        return false;
    }
}
