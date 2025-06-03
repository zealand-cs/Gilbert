package dk.zealandcs.gilbert.config;

import dk.zealandcs.gilbert.application.post.IPostService;
import dk.zealandcs.gilbert.application.user.IUserService;
import dk.zealandcs.gilbert.domain.post.Post;
import dk.zealandcs.gilbert.domain.post.ProductType;
import dk.zealandcs.gilbert.domain.tree.Tree;
import dk.zealandcs.gilbert.domain.user.User;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);
    private final IPostService postService;
    private final IUserService userService;

    GlobalControllerAdvice(IPostService postService, IUserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @ModelAttribute("currentUser")
    public Optional<User> getCurrentUser(HttpSession session) {
        var user = (User)session.getAttribute("currentUser");
        return Optional.ofNullable(user);
    }

    @ModelAttribute("currentUserFavorites")
    public List<Post> getCurrentUserFavorites(HttpSession session) {
        var user = (User)session.getAttribute("currentUser");

        if (user == null) {
            return new ArrayList<>();
        }

        return userService.getFavorites(user);
    }

    @ModelAttribute("categoryTree")
    public Tree categoryTree() {
        var categories = postService.getAllProductTypes();
        var s = new Tree<>(categories, ProductType::getId, ProductType::getParentId);
        logger.info("Get root: {}", s.getRoots());
        return s;
    }
}
