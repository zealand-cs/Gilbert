package dk.zealandcs.gilbert.config.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LoggedInFilter extends OncePerRequestFilter {
    private final List<String> urlExclusionList = List.of("/auth/logout");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilterInternal(HttpServletRequest req, @NotNull HttpServletResponse res, @NotNull FilterChain filterChain) throws IOException, ServletException {
        var user = Optional.ofNullable(req.getSession().getAttribute("currentUser"));

        if (user.isPresent()) {
            var redirect = "/";
            req = new HttpServletRequestWrapper(req) {
                @Override
                public String getRequestURI() {
                    return redirect;
                }
            };
            res.sendRedirect(redirect);
        }

        filterChain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) throws ServletException {
        return urlExclusionList.stream().anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }
}
