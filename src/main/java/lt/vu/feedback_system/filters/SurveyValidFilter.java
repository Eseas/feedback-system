package lt.vu.feedback_system.filters;

import lt.vu.feedback_system.businesslogic.surveys.SurveyContext;
import lt.vu.feedback_system.businesslogic.users.UserContext;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SurveyValidFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!response.isCommitted()) {
            SurveyContext surveyContext = (SurveyContext) ((HttpServletRequest) request).getSession().getAttribute("surveyContext");

            try {
                String surveyLink = request.getParameter("s");

                if (!surveyContext.isSurveyValid(surveyLink)) {
                    String contextPath = ((HttpServletRequest) request).getContextPath();

                    ((HttpServletResponse) response).sendRedirect(contextPath + "/expired-survey.html");
                }

            } catch (NullPointerException ex) {
            } catch (javax.persistence.NoResultException ex) {
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
