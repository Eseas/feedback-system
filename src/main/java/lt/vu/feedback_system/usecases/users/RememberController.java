package lt.vu.feedback_system.usecases.users;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.inject.Model;

@Model
@Slf4j
public class RememberController {

    @Getter
    @Setter
    private String email;

    public void rememberPassword() {

    }
}
