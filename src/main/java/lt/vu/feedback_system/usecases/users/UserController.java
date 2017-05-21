package lt.vu.feedback_system.usecases.users;

import lombok.extern.slf4j.Slf4j;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.entities.User;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
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
        return userContext.getUser().getId();
    }
}
