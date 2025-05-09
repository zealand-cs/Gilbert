package dk.zealandcs.gilbert.presentation;

import dk.zealandcs.gilbert.application.user.IUserService;
import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.RegisterUser;
import dk.zealandcs.gilbert.domain.validators.EmailValidator;
import dk.zealandcs.gilbert.exceptions.EmailInUseException;
import dk.zealandcs.gilbert.exceptions.EmailNotFoundException;
import dk.zealandcs.gilbert.exceptions.InvalidEmailFormatException;
import dk.zealandcs.gilbert.exceptions.InvalidPasswordException;
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

    AuthController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String authPage(@RequestParam Optional<String> redirect, Model model) {
        var redirectParam = redirect.map(s -> "?redirect=" + s).orElse("");
        model.addAttribute("url", "/auth/entry" + redirectParam);
        return "auth/index";
    }

    @PostMapping("/entry")
    public String authPageRedirect(@RequestParam String email, @RequestParam Optional<String> redirect, Model model) {
        try {
            EmailValidator.isValid(email);
        } catch (InvalidEmailFormatException e) {
            model.addAttribute("emailError", true);
            return "auth/index";
        }

        logger.info("Getting user by email: {}", email);
        var user = userService.getUserByEmail(email);

        model.addAttribute("email", email);

        var redirectParam = redirect.map(s -> "?redirect=" + s).orElse("");

        if (user.isEmpty()) {
            model.addAttribute("registerUrl", "/auth/register" + redirectParam);
            return "auth/register";
        } else {
            model.addAttribute("loginUrl", "/auth/login" + redirectParam);
            return "auth/login";
        }
    }

    @PostMapping("/login")
    public String loginRequest(@RequestParam String email, @RequestParam String password, @RequestParam Optional<String> redirect, HttpSession session, Model model) {
        logger.info("Login attempt for email: {}", email);

        try {
            var loggedIn = userService.login(email, password);

            logger.info("Login attempt successful for email {}", email);
            session.setAttribute("currentUser", loggedIn);

            if (redirect.isPresent()) {
                logger.debug("Redirecting to {}", redirect.get());
                return "redirect:" + redirect.get();
            } else {
                logger.debug("No redirect url found. Redirecting to front page.");
                return "redirect:../";
            }
        } catch (EmailNotFoundException | InvalidPasswordException e) {
            logger.warn("Login failed for {}", email, e);
            model.addAttribute("error", "Ugyldig email eller adgangskode");
            return "auth/login";
        }
    }

    @PostMapping("/register")
    public String registerRequest(@ModelAttribute RegisterUser user, @RequestParam Optional<String> redirect, HttpSession session, Model model) {
        logger.info("Registration for email: {}", user.getEmail());
        try {
            // TODO: Catch all errors and show
            var registered = userService.register(user);

            logger.debug("Successful registration with {}", user.getEmail());
            session.setAttribute("currentUser", registered);

            if (redirect.isPresent()) {
                logger.debug("Redirecting to {}", redirect.get());
                return "redirect:" + redirect.get();
            } else {
                logger.debug("No redirect url found. Redirecting to front page.");
                return "redirect:/";
            }
        } catch (EmailInUseException e) {
            logger.warn("Email already in use: {}", user.getEmail());
            model.addAttribute("error", "Email already in use");
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
