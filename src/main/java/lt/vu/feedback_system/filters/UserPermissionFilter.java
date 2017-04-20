package lt.vu.feedback_system.filters;

import lt.vu.feedback_system.businesslogic.users.Session;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter checks if LoginController has loginIn and admin property set to true.
 * If it is not set then request is being redirected to the login.xhml page.
 *
 */
public class UserPermissionFilter implements Filter {

    /**
     * Checks if user is logged in. If not it redirects to the login.xhtml page.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!response.isCommitted()) {
            Session session = (Session) ((HttpServletRequest) request).getSession().getAttribute("session");

            if (session == null || !session.isLoggedIn()) {
                String contextPath = ((HttpServletRequest) request).getContextPath();
                ((HttpServletResponse) response).sendRedirect(contextPath + "/login.html");
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}