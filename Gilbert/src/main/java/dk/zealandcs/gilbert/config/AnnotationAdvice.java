package dk.zealandcs.gilbert.config;

import dk.zealandcs.gilbert.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
public class AnnotationAdvice {
    @ModelAttribute("currentUser")
    public Optional<User> getCurrentUser(HttpSession session) {
        var user = (User)session.getAttribute("currentUser");
        return Optional.ofNullable(user);
    }
}
