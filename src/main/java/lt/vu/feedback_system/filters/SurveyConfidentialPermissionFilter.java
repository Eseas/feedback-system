package lt.vu.feedback_system.filters;

import lt.vu.feedback_system.businesslogic.surveys.SurveyContext;
import lt.vu.feedback_system.businesslogic.users.UserContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter checks if user can access survey data.
 * If it can't then request is being redirected to the index.xhml page.
 *
 */
public class SurveyConfidentialPermissionFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!response.isCommitted()) {
            UserContext userContext = (UserContext) ((HttpServletRequest) request).getSession().getAttribute("userContext");
            SurveyContext surveyContext = (SurveyContext) ((HttpServletRequest) request).getSession().getAttribute("surveyContext");

            if (surveyContext == null) { // If we can't get context, try to refresh
                ((HttpServletResponse) response).sendRedirect(
                        ((HttpServletRequest) request).getRequestURI()
                        + "?"
                        + ((HttpServletRequest) request).getQueryString());
            } else {
                try {
                    String surveyLink = request.getParameter("s");

                    if (surveyContext.isSurveyConfidential(surveyLink)
                            && (userContext == null || (!userContext.isAdmin()
                            && !surveyContext.isSurveyCreator(surveyLink)))) {
                        String contextPath = ((HttpServletRequest) request).getContextPath();

                        ((HttpServletResponse) response).sendRedirect(
                                contextPath + "/index.html");
                    }
                } catch (NullPointerException ex) {
                } catch (NumberFormatException ex) {
                }
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
