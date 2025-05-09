package dk.zealandcs.gilbert.presentation.profile;

import dk.zealandcs.gilbert.application.user.IUserService;
import dk.zealandcs.gilbert.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProfileController.class);

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
}
