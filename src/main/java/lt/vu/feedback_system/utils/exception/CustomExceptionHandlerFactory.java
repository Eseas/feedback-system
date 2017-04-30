package lt.vu.feedback_system.utils.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class CustomExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;

    public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    public ExceptionHandler getExceptionHandler() {
        return new CustomExceptionHandler (parent.getExceptionHandler());

    }

}
