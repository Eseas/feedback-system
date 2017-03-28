package lt.vu.usecases.cdi.conversation;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
@Slf4j
public class LoginController implements Serializable {

}