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
@RequestMapping("/profile")
public class ProfileController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final IUserService userService;

    ProfileController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me/{*path}")
    public String profileForward(@PathVariable String path, HttpSession session) {
        var user = Optional.ofNullable((User) session.getAttribute("currentUser"));

        return user.map(value -> "forward:/profile/@" + value.getUsername() + path).orElse("redirect:/auth");
    }

    @GetMapping("/@{username}")
    public String profilePage(@PathVariable String username, HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
        var user = (User) request.getAttribute("profileUser");
        boolean self = (boolean) request.getAttribute("self");

        model.addAttribute("profileUser", user);
        model.addAttribute("self", self);

        return "profile/posts";
    }

    @GetMapping("/@{username}/posts")
    public String postsPage(@PathVariable String username, HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
        return "forward:/profile/@" + username;
    }

    @GetMapping("/@{username}/sales")
    public String salesPage(@PathVariable String username, HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
        return "forward:/profile/@" + username;
    }

    @GetMapping("/@{username}/orders")
    public String ordersPage(@PathVariable String username, HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
        return "forward:/profile/@" + username;
    }

    @GetMapping("/@{username}/info")
    public String infoPage(@PathVariable String username, HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
        return "forward:/profile/@" + username;
    }

    @GetMapping("/@{username}/help")
    public String helpPage(@PathVariable String username, HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
        return "forward:/profile/@" + username;
    }

    @GetMapping("/@{username}/pfp")
    public String profilePicture(HttpServletRequest request) {
        var profileUser = (User) request.getAttribute("profileUser");
        if (profileUser.getProfilePictureId().isPresent()) {
            var url = userService.getProfilePictureUrl(profileUser.getProfilePictureId().get());
            return "redirect:" + url;
        }
        return "redirect:/images/icons/profile.svg";
    }

    @GetMapping("/@{username}/settings")
    public String settingsPage(HttpSession session, Model model) {
        return "/profile/settings/index";
    }

    @GetMapping("/@{username}/settings/account")
    public String accountSettingsPage(HttpSession session, Model model) {
        return "/profile/settings/account";
    }

    @PostMapping("/@{username}/settings/account/pfp")
    public String updateProfilePicture(@RequestParam MultipartFile profilePicture, HttpServletRequest request, HttpSession session) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));
        var profileUser = (User) request.getAttribute("profileUser");
        boolean self = (boolean) request.getAttribute("self");

        if (currentUser.isEmpty() || (!self && !currentUser.get().getRole().isAtLeast(UserRole.Employee))) {
            // Not allowed or not logged in.
            return "redirect:/";
        }

        userService.updateProfilePicture(profileUser, profilePicture);

        return "redirect:/profile/@" + profileUser.getUsername() + "/settings/account";
    }

    @PostMapping("/@{username}/settings/account/password")
    public String updatePasswordSetting(@RequestParam String currentPassword, @RequestParam String newPassword, HttpServletRequest request, HttpSession session, Model model) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));
        var profileUser = (User) request.getAttribute("profileUser");
        boolean self = (boolean) request.getAttribute("self");

        if (currentUser.isEmpty() || (!self && !currentUser.get().getRole().isAtLeast(UserRole.Admin))) {
            // Not allowed or not logged in.
            return "redirect:/";
        }

        try {
            if (userService.updatePassword(profileUser, currentPassword, newPassword) && self) {
                session.invalidate();
                return "redirect:/";
            }
        } catch (InvalidPasswordFormatException e) {
            return "redirect:/profile/@" + profileUser.getUsername() + "/settings/account";
        }

        return "redirect:/profile/@" + profileUser.getUsername() + "/settings/account";
    }

    @PostMapping("/@{username}/settings/account/delete")
    public String deleteAccountSetting(HttpServletRequest request, HttpSession session, Model model) {
        var currentUser = Optional.ofNullable((User) session.getAttribute("currentUser"));
        var profileUser = (User) request.getAttribute("profileUser");
        boolean self = (boolean) request.getAttribute("self");

        if (currentUser.isEmpty() || (!self && !currentUser.get().getRole().isAtLeast(UserRole.Admin))) {
            // Not allowed or not logged in.
            return "redirect:/";
        }

        if (userService.deleteUser(profileUser)) {
            if (self) {
                session.invalidate();
            }

            return "redirect:/";
        }

        return "redirect:/profile/@" + profileUser.getUsername() + "/settings/account";
    }
}
