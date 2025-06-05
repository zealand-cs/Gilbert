package dk.zealandcs.gilbert.presentation.admin;


import dk.zealandcs.gilbert.application.post.IPostService;
import dk.zealandcs.gilbert.application.post.PostService;
import dk.zealandcs.gilbert.application.user.IUserService;
import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.UserRole;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/adminpanel")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final IUserService userService;
    private final IPostService postService;

    public AdminController(IUserService userService, IPostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("")
    public String getAdminPanel(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.getRole().isAtLeast(UserRole.EMPLOYEE)) {
            logger.warn("Unauthorized access attempt to admin panel");
            return "redirect:/auth?redirect=/adminpanel";
        }

        List<User> users = userService.allUsers();


        long adminCount = users.stream().filter(u -> u.getRole() == UserRole.ADMIN).count();
        long employeeCount = users.stream().filter(u -> u.getRole() == UserRole.EMPLOYEE).count();
        long sellerCount = users.stream().filter(u -> u.getRole() == UserRole.SELLER).count();
        long userCount = users.stream().filter(u -> u.getRole() == UserRole.USER).count();


        model.addAttribute("users", users);
        model.addAttribute("adminCount", adminCount);
        model.addAttribute("employeeCount", employeeCount);
        model.addAttribute("sellerCount", sellerCount);
        model.addAttribute("userCount", userCount);


        logger.info("Admin panel accessed by user: {}", currentUser.getUsername());
        return "admin/adminfrontpage";
    }



    @GetMapping("/users")
    public String getUsersPage(HttpSession session, Model model) {

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.getRole().isAtLeast(UserRole.EMPLOYEE)) {
            logger.warn("Unauthorized access attempt to admin panel");
            return "redirect:/auth?redirect=/admin/users";
        }

        logger.info("Admin panel accessed by user: {}", currentUser.getUsername());
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        return "admin/user-management";
    }


    @PostMapping("/updateRole")
    public String updateUserRole(@RequestParam int userId,
                                 @RequestParam UserRole newRole,
                                 HttpSession session) {

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.getRole().isAtLeast(UserRole.EMPLOYEE))  {
            logger.warn("Unauthorized role update attempt");
            return "redirect:/auth?redirect=/adminpanel";
        }

        logger.info("User role update: User ID {} changing to role {} by {}",
                userId, newRole, currentUser.getUsername());


        Optional<User> userToUpdate = userService.getUser(userId);
        if (userToUpdate.isEmpty()) {
            logger.warn("User with ID {} not found", userId);
            return "redirect:/adminpanel";
        }


        User updatedUser = userToUpdate.get();
        updatedUser.setRole(newRole);
        userService.updateUser(updatedUser);

        return "redirect:/adminpanel";

    }

    @PostMapping("/adminpost/approve")
    public String approvePost(@RequestParam int postId) {
        return "redirect:/adminpost";
    }

    @PostMapping("/adminpost/decline")
    public String declinePost(@RequestParam int postId) {
        return "redirect:/adminpost";
    }
}
