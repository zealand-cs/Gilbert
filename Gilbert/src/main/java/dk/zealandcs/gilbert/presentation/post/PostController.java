package dk.zealandcs.gilbert.presentation.post;

import dk.zealandcs.gilbert.domain.post.Condition;
import org.springframework.ui.Model;
import dk.zealandcs.gilbert.application.post.IPostService;
import dk.zealandcs.gilbert.domain.post.Post;
import dk.zealandcs.gilbert.domain.user.User;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final IPostService postService;

    //Constructor injection of repository dependency
    PostController(IPostService postService) { this.postService = postService; }


    @PostMapping
    public String createPost(@ModelAttribute Post post, HttpSession session, Model model) {
        var currentUser = Optional.ofNullable((User)session.getAttribute("currentUser"));
        if (currentUser.isEmpty()) {
            logger.warn("Unauthenticated User");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthenticated User");
        }

        post.setOwnerId(currentUser.get().getId());
        post.setDatePostedAt(new Date());

        var newPost = postService.createPost(post);
            if (newPost.isEmpty()) {
                logger.error("Failed to create new post");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create new post");
            }
            logger.info("New post created with ID" + newPost.get().getId());
            return "redirect:/posts/" + newPost.get().getId();
    }

    @GetMapping("/createpost")
    public String createPostPage(HttpSession session, Model model) {
        var currentUser = Optional.ofNullable((User)session.getAttribute("currentUser"));
        if (currentUser.isEmpty()) {
            logger.warn("Redirecting user to login page");
            return "redirect:/auth?redirect=/posts/createpost";
        }

        model.addAttribute("brands", postService.getAllBrands());
        model.addAttribute("types", postService.getAllProductTypes());
        model.addAttribute("conditions", Condition.values());
        return "post/create";
    }

    @GetMapping
    public String getAllPosts(Model model) {
        List<Post> posts = postService.allPosts();
        model.addAttribute("posts", posts);
        return "post/all";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable int id, Model model) {
        var post = postService.findById(id);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        model.addAttribute("post", post.get());
        return "post/details";
    }

    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable int postId, HttpSession session, Model model) {
        var currentUser = Optional.ofNullable((User)session.getAttribute("currentUser"));
        if (currentUser.isEmpty()) {
            logger.warn("Redirecting user to login page");
            return "redirect:/auth";
        }
        var post = postService.getPost(postId);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        if (postService.deletePost(currentUser.get(), post.get())) {
            logger.info("Post deleted with id {}", postId);
            return "redirect:/posts";
        }
        logger.warn("User {} is not allowed to delete post {}", currentUser.get().getUsername(), post.get().getName());
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User is not allowed to delete post");
    }




}
