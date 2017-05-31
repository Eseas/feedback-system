package lt.vu.feedback_system.filters;

import lt.vu.feedback_system.businesslogic.surveys.SurveyContext;
import lt.vu.feedback_system.businesslogic.users.UserContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter checks if user is survey's creator.
 * If it isn't then request is being redirected to the index.xhml page.
 *
 */
public class SurveyCreatorPermissionFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!response.isCommitted()) {
            UserContext userContext = (UserContext) ((HttpServletRequest) request).getSession().getAttribute("userContext");
            SurveyContext surveyContext = (SurveyContext) ((HttpServletRequest) request).getSession().getAttribute("surveyContext");

            try {
                String surveyLink = request.getParameter("s");

                if (!userContext.isAdmin() && !surveyContext.isSurveyCreator(surveyLink)) {
                    String contextPath = ((HttpServletRequest) request).getContextPath();
                    ((HttpServletResponse) response).sendRedirect(contextPath + "/index.html");
                }

            } catch (NullPointerException ex) {
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
