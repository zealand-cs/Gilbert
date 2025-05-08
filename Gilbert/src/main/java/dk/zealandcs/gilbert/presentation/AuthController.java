package dk.zealandcs.gilbert.presentation;

import dk.zealandcs.gilbert.application.user.IUserService;
import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.UserRegister;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final IUserService userService;

    AuthController(IUserService userService) { this.userService = userService; }

    @GetMapping
    public String authPage(@RequestParam(required = false) String redirect, Model model) {
        // Optional is not used here, since we can just pass a nullable object to the model.
        // This spares us an if statement that is made in the model anyways.
        model.addAttribute("redirect", redirect);
        return "auth/index";
    }

    @PostMapping("/redirect")
    public String authPageRedirect(@RequestParam String email, @RequestParam Optional<String> redirect) {
        logger.info("Getting user by email: {}", email);
        var user = userService.getUserByEmail(email);

        var redirectParam = "";
        if (redirect.isPresent()) {
            logger.info("Redirect found: {}", redirect.get());
            redirectParam = "&redirect=" + redirect.get();
        }

        if (user.isEmpty()) {
            return "redirect:/auth/register?email=" + email + redirectParam;
        }
        else {
            return "redirect:/auth/login?email=" + email + redirectParam;
        }
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String email, @RequestParam(required = false) String redirect, Model model) {
        logger.info("Email found: {}", email);
        model.addAttribute("email", email);
        model.addAttribute("redirect", redirect);

        return "auth/login";
    }

    @PostMapping("/login")
    public String loginRequest(@ModelAttribute User user, @RequestParam Optional<String> redirect, HttpSession session, Model model) {
        logger.info("Login attempt for email: {}", user.getEmail());
        var loggedIn = userService.login(user.getEmail(), user.getPassword());

        if (loggedIn.isEmpty()) {
            logger.warn("Login failed for email: {}", user.getEmail());
            model.addAttribute("error", "Ugyldig email eller adgangskode");
            return "auth/login";
        } else {
            logger.info("Login attempt successful for email {}", user.getEmail());
            session.setAttribute("currentUser", loggedIn.get());

            if (redirect.isPresent()) {
                logger.debug("Redirecting to {}", redirect.get());
                return "redirect:" + redirect.get();
            } else {
                logger.debug("No redirect url found. Redirecting to front page.");
                return "redirect:/";
            }
        }
    }

    @GetMapping("/register")
    public String registerPage(@RequestParam(required = false) String email, @RequestParam(value = "redirect", required = false) String redirectUrl, Model model) {
        logger.info("Email found: {}", email);
        model.addAttribute("email", email);
        model.addAttribute("redirect", redirectUrl);

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerRequest(@ModelAttribute UserRegister user, @RequestParam Optional<String> redirect, HttpSession session, Model model) {
        logger.info("Registration for email: {}", user.getEmail());
        var registered = userService.register(user);

        if (registered.isEmpty()) {
            logger.warn("Registration failed for email: {}", user.getEmail());
            model.addAttribute("error", "Email already in use");
            return "auth/register";
        }

        logger.debug("Successful registration with {}", user.getEmail());
        session.setAttribute("currentUser", registered);

        if (redirect.isPresent()) {
            logger.debug("Redirecting to {}", redirect.get());
            return "redirect:" + redirect.get();
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
