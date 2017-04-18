package lt.vu.feedback_system.filters;

import lt.vu.feedback_system.businesslogic.users.Session;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter checks if LoginController has loginIn property set to true.
 * If it is set then request is being redirected to the index.xhml page.
 *
 */
public class NoLoginAgainFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!response.isCommitted()) {
            Session session = (Session) ((HttpServletRequest) request).getSession().getAttribute("session");

            if (session != null && session.isLoggedIn()) {
                String contextPath = ((HttpServletRequest) request).getContextPath();
                ((HttpServletResponse) response).sendRedirect(contextPath + "/index.html");
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
