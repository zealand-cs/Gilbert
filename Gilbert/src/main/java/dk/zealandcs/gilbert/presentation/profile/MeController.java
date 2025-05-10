package dk.zealandcs.gilbert.presentation.profile;

import dk.zealandcs.gilbert.domain.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * A simple class that redirects all "/me" endpoints to the corresponding profile
 * page.
 */
@Controller
@RequestMapping("/me/{*path}")
public class MeController {
    @GetMapping
    public String profileForward(@PathVariable String path, HttpSession session) {
        var user = Optional.ofNullable((User) session.getAttribute("currentUser"));

        if (user.isEmpty()) {
            return "redirect:/auth";
        }

        return "redirect:/profile/@" + user.get().getUsername() + path;
    }
}
