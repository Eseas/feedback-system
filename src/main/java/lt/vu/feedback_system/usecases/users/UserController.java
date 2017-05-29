package lt.vu.feedback_system.usecases.users;

import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.entities.User;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class UserController {

    @Inject
    private UserContext userContext;

    public boolean isAdmin() {
        return userContext.isAdmin();
    }

    public String getFullName() {
        User user =  userContext.getUser();

        return user.getFirstName() + " " + user.getLastName();
    }

    public Integer getId() {
        User user = userContext.getUser();

        if (user != null)
            return user.getId();
        else
            return null;
    }
}
