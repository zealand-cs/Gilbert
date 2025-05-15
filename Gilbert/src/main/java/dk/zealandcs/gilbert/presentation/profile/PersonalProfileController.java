package dk.zealandcs.gilbert.presentation.profile;

import dk.zealandcs.gilbert.application.user.IUserService;
import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.UserRole;
import dk.zealandcs.gilbert.exceptions.InvalidPasswordFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("/profile/me")
public class PersonalProfileController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PersonalProfileController.class);

    private final IUserService userService;

    PersonalProfileController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profilePage(HttpSession session, Model model) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));
        if (currentUser.isEmpty()) {
            return "redirect:/";
        }

        return "forward:/profile/@" + currentUser.get().getUsername() + "/posts";
    }

    @GetMapping("/favorites")
    public String favorite(HttpSession session, Model model) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));
        if (currentUser.isEmpty()) {
            return "redirect:/";
        }

        return "forward:/profile/@" + currentUser.get().getUsername() + "/favorites";
    }

    @GetMapping("/settings")
    public String settingsPage(HttpSession session, Model model) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));
        if (currentUser.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("profileUser", currentUser.get());
        return "/profile/settings/index";
    }

    @GetMapping("/settings/account")
    public String accountSettingsPage(HttpSession session, Model model) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));
        if (currentUser.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("profileUser", currentUser.get());
        return "/profile/settings/account";
    }

    @PostMapping("/settings/account/details")
    public String updateDetails(@RequestParam String displayName, @RequestParam String username, HttpSession session) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));

        if (currentUser.isEmpty()) {
            // Not allowed or not logged in.
            return "redirect:/";
        }

        currentUser.get().setDisplayName(displayName);
        currentUser.get().setUsername(username);
        userService.updateUser(currentUser.get());

        return "redirect:/profile/me/settings/account";
    }

    @PostMapping("/settings/account/pfp")
    public String updateProfilePicture(@RequestParam MultipartFile profilePicture, HttpSession session) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));

        if (currentUser.isEmpty()) {
            // Not allowed or not logged in.
            return "redirect:/";
        }

        userService.updateProfilePicture(currentUser.get(), profilePicture);

        return "redirect:/profile/me/settings/account";
    }

    @PostMapping("/settings/account/password")
    public String updatePasswordSetting(@RequestParam String currentPassword, @RequestParam String newPassword, HttpSession session) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));

        if (currentUser.isEmpty()) {
            // Not allowed or not logged in.
            return "redirect:/";
        }

        try {
            if (userService.updatePassword(currentUser.get(), currentPassword, newPassword)) {
                session.invalidate();
                return "redirect:/";
            }
        } catch (InvalidPasswordFormatException e) {
            return "redirect:/profile/me/settings/account";
        }

        return "redirect:/profile/me/settings/account";
    }

    @PostMapping("/settings/account/delete")
    public String deleteAccountSetting(HttpSession session) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));

        if (currentUser.isEmpty()) {
            // Not allowed or not logged in.
            return "redirect:/";
        }

        if (userService.deleteUser(currentUser.get())) {
            session.invalidate();

            return "redirect:/";
        }

        return "redirect:/profile/me/settings/account";
    }

    @PostMapping("/favorites/{postId}")
    public String addFavorite(@PathVariable int postId, HttpSession session) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));
        if (currentUser.isEmpty()) {
            // Not allowed or not logged in.
            return "redirect:/";
        }

        userService.addFavorite(currentUser.get(), postId);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/favorites/{postId}/delete")
    public String deleteFavorite(@PathVariable int postId, HttpSession session) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));
        if (currentUser.isEmpty()) {
            // Not allowed or not logged in.
            return "redirect:/";
        }

        userService.removeFavorite(currentUser.get(), postId);
        return "redirect:/posts/" + postId;
    }
}
