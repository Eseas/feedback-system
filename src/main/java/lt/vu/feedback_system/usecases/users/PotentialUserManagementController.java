package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lt.vu.feedback_system.businesslogic.users.UserLogic;
import lt.vu.feedback_system.dao.PotentialUserDAO;
import lt.vu.feedback_system.dao.RegKeyDAO;
import lt.vu.feedback_system.entities.PotentialUser;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PotentialUserManagementController implements Serializable {

    @Getter
    private PotentialUser potentialUser = new PotentialUser();

    @Inject
    private PotentialUserDAO potentialUserDAO;

    @Inject
    private RegKeyDAO regKeyDAO;

    @Inject
    private UserLogic userLogic;

    public List<PotentialUser> getAllPotentialUsers() {
        return potentialUserDAO.getAllPotentialUsers();
    }

    public void createPotentialUser() {
        potentialUser.setEmail(potentialUser.getEmail().toLowerCase());
        final String email = potentialUser.getEmail();

        if (!userLogic.isEmailFree(email)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate", "This email is already in the list"));
        }
        if (!userLogic.isEmailFormatValid(email)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid format", "This email is of invalid format"));
        }
        if (userLogic.isEmailFree(email) && userLogic.isEmailFormatValid(email)) {
            userLogic.createPotentialUser(potentialUser);
            potentialUser = new PotentialUser();
        }
    }

    public void removePotentialUser(PotentialUser potentialUser) {
        userLogic.removePotentialUser(potentialUser);
    }
}
