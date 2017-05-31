package lt.vu.feedback_system.filters;

import lt.vu.feedback_system.businesslogic.surveys.SurveyContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestSurveyContextFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!response.isCommitted()) {
            SurveyContext surveyContext = (SurveyContext) ((HttpServletRequest) request).getSession().getAttribute("surveyContext");

            if (surveyContext == null) { // If we can't get context, try to refresh
                ((HttpServletResponse) response).sendRedirect(
                        ((HttpServletRequest) request).getRequestURI()
                                + "?"
                                + ((HttpServletRequest) request).getQueryString());
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
