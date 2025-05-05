package dk.zealandcs.gilbert.presentation;

import dk.zealandcs.gilbert.application.user.IUserService;
import dk.zealandcs.gilbert.domain.User;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final IUserService userService;

    AuthController(IUserService userService) { this.userService = userService; }

    @GetMapping
    public String authPage(@RequestParam(value = "redirect", required = false) String redirectUrl, Model model) {
        if (redirectUrl != null) {
            model.addAttribute("redirect", redirectUrl);
            logger.debug("Redirect URL found: {}", redirectUrl);
        }

        return "auth/index";
    }

    @GetMapping("/redirect")
    public String authPageRedirect() {
        return "auth/index";
    }

    @GetMapping("/login")
    public String loginPage(@ModelAttribute User user, @RequestParam(value = "redirect", required = false) String redirectUrl, Model model) {
        if (redirectUrl != null) {
            model.addAttribute("redirect", redirectUrl);
            logger.debug("Redirect URL found: {}", redirectUrl);
        }

        return "auth/login";
    }

    @PostMapping("/login")
    public String loginRequest(@ModelAttribute User user, @RequestParam(value = "redirect", required = false) String redirectUrl, HttpSession session, Model model) {
        logger.info("Login attempt for email: {}", user.getEmail());
        var loggedIn = userService.login(user.getEmail(), user.getPassword());

        if (loggedIn.isEmpty()) {
            logger.warn("Login failed for email: {}", user.getEmail());
            model.addAttribute("error", "Ugyldig email eller adgangskode");
            return "auth/login";
        } else {
            logger.info("Login attempt successful for email {}", user.getEmail());
            session.setAttribute("currentUser", loggedIn.get());

            if (redirectUrl != null) {
                logger.debug("Redirecting to {}", redirectUrl);
                return "redirect:" + redirectUrl;
            } else {
                logger.debug("No redirect url found. Redirecting to front page.");
                return "redirect:/";
            }
        }
    }

    @PostMapping("/register")
    public String registerRequest(@ModelAttribute User user, @RequestParam(value = "redirect", required = false) String redirectUrl, HttpSession session, Model model) {
        logger.info("Registration for email: {}", user.getEmail());
        var registered = userService.register(user);

        if (registered.isEmpty()) {
            logger.warn("Registration failed for email: {}", user.getEmail());
            model.addAttribute("error", "Email already in use");
            return "auth/register";
        }

        logger.debug("Successful registration with {}", user.getEmail());
        session.setAttribute("currentUser", registered);

        if (redirectUrl != null) {
            logger.debug("Redirecting to {}", redirectUrl);
            return "redirect:" + redirectUrl;
        } else {
            logger.debug("No redirect url found. Redirecting to front page.");
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
