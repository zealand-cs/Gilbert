package dk.zealandcs.gilbert.presentation.profile;

import dk.zealandcs.gilbert.application.user.IUserService;
import dk.zealandcs.gilbert.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Optional;

@Component
public class ProfileInterceptor implements HandlerInterceptor {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProfileInterceptor.class);
    private final IUserService userService;

    private ProfileInterceptor(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Intercepting profile request");

        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        var username = Optional.ofNullable((String) pathVariables.get("username"));

        if (username.isEmpty()) {
            return false;
        }

        logger.info("Get username {}", username.get());

        var session = request.getSession();

        Optional<User> currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));

        if (currentUser.isPresent() && currentUser.get().getUsername().equals(username.get())) {
            request.setAttribute("profileUser", currentUser.get());
            request.setAttribute("self", true);
            logger.info("User views itself {}", username.get());
        } else {
            var user = userService.getUserByUsername(username.get());
            if (user.isEmpty()) {
                return false;
            }

            request.setAttribute("profileUser", user.get());
            request.setAttribute("self", false);
            logger.info("User views another {}", username.get());
        }

        return true;
    }
}
