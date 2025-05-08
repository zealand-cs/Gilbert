package dk.zealandcs.gilbert.config.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class LoggedInFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;

        var user = Optional.ofNullable(req.getSession().getAttribute("currentUser"));

        if (user.isPresent()) {
            var redirect = "/";
            req = new HttpServletRequestWrapper((HttpServletRequest) request) {
                @Override
                public String getRequestURI() {
                    return redirect;
                }
            };
            res.sendRedirect(redirect);
        }

        filterChain.doFilter(req, res);
    }
}
