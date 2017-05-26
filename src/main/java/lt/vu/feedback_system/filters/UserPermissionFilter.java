package lt.vu.feedback_system.filters;

import lt.vu.feedback_system.businesslogic.users.UserContext;
import lt.vu.feedback_system.utils.FacesUtil;

import javax.faces.context.FacesContext;
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
            UserContext userContext = (UserContext) ((HttpServletRequest) request).getSession().getAttribute("userContext");


            if (userContext == null || !userContext.isLoggedIn() || userContext.isBlocked()) {
                String contextPath = ((HttpServletRequest) request).getContextPath();
                String redirectUrl = FacesUtil.encodeRedirect(
                        contextPath + "/login.html",
                        ((HttpServletRequest) request).getRequestURI(),
                        ((HttpServletRequest) request).getQueryString()
                );

                ((HttpServletResponse) response)
                        .sendRedirect(redirectUrl);

                if (userContext != null && userContext.isBlocked()) {
                    userContext.logout();
                    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
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
