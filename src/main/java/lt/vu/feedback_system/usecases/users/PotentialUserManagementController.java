package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lt.vu.feedback_system.businesslogic.users.UserLogic;
import lt.vu.feedback_system.dao.RegKeyDAO;
import lt.vu.feedback_system.entities.PotentialUser;

import javax.annotation.PostConstruct;
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
    private RegKeyDAO regKeyDAO;

    @Inject
    private UserLogic userLogic;

    @Getter
    private List<PotentialUser> potentialUsers;

    @PostConstruct
    public void reloadAllPotentialUsers() {
        potentialUsers = userLogic.getAllPotentialUsers();
    }

    public void createPotentialUser() {
        if (!userLogic.isEmailFree(potentialUser.getEmail())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate", "This email is already in the list"));
        }
        if (!userLogic.isEmailFormatValid(potentialUser.getEmail())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid format", "This email is of invalid format"));
        }
        if (userLogic.isEmailFree(potentialUser.getEmail()) && userLogic.isEmailFormatValid(potentialUser.getEmail())) {
            userLogic.createPotentialUser(potentialUser);
            potentialUser = new PotentialUser();
        }
        reloadAllPotentialUsers();
    }

    public void removePotentialUser(PotentialUser potentialUser) {
        userLogic.removePotentialUser(potentialUser);
        reloadAllPotentialUsers();
    }
}
