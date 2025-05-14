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

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final IUserService userService;

    ProfileController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/@{username}")
    public String profilePage(HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
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

    @GetMapping("/@{username}/pfp")
    public String profilePicture(HttpServletRequest request) {
        var profileUser = (User) request.getAttribute("profileUser");
        if (profileUser.getProfilePictureId().isPresent()) {
            var url = userService.getProfilePictureUrl(profileUser.getProfilePictureId().get());
            return "redirect:" + url;
        }
        return "redirect:/images/icons/profile.svg";
    }
}
