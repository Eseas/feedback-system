package lt.vu.feedback_system.businesslogic.interceptors_impl;

import lt.vu.feedback_system.businesslogic.interceptors.Logged;
import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.dao.LogDAO;
import lt.vu.feedback_system.entities.Log;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggedInterceptor implements Serializable {

    @Inject
    private UserContext userContext;

    @Inject
    private LogDAO logDAO;

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        StringBuilder logText = new StringBuilder();

        if (userContext != null && userContext.getUser() != null) {
            logText.append("uid:");
            logText.append(userContext.getUser().getId());
            logText.append(":adm:");
            logText.append(userContext.isAdmin());
        } else {
            logText.append("uid:null");
        }
        logText.append(":method:");
        logText.append(context.getMethod().getDeclaringClass().getSimpleName());
        logText.append("[");
        logText.append(context.getMethod().getName());
        logText.append("]");

        logDAO.create(new Log(logText.toString()));

        return context.proceed();
    }
}
