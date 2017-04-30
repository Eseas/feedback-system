package lt.vu.feedback_system.utils.exception;

import javax.faces.FacesException;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;
import java.util.Map;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private final ExceptionHandler wrapped;

    public CustomExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;

    }
    public void handle() throws FacesException {
        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();

        while (i.hasNext()) {
            /*ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context =
                    (ExceptionQueuedEventContext) event.getSource();
            // get the exception from context
            Throwable t = context.getException();
            final FacesContext fc = FacesContext.getCurrentInstance();
            final ExternalContext externalContext = fc.getExternalContext();
            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
            final ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            //here you do what ever you want with exception*/
            try {
                //log error ?
                //logger.error("Severe Exception Occured");
                //log.log(Level.SEVERE, "Critical Exception!", t);
                //redirect error page
                /*requestMap.put("exceptionMessage", t.getMessage());
                nav.performNavigation("/TestPRoject/error.xhtml");
                fc.renderResponse();*/
                // remove the comment below if you want to report the error in a jsf error message
                //JsfUtil.addErrorMessage(t.getMessage());
            }
            finally {
                //remove it from queue
                i.remove();
            }
        }
        //parent hanle
        getWrapped().handle();
    }

}
