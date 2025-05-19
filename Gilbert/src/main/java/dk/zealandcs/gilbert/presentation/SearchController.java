package dk.zealandcs.gilbert.presentation;

import dk.zealandcs.gilbert.application.post.IPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final IPostService postService;

    SearchController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String mainSearchPage(@RequestParam Optional<String[]> category, @RequestParam Optional<String> query, Model model) {
        if (category.isPresent()) {
            var posts = postService.search(null, category.get());
            model.addAttribute("posts", posts);
        }
        if (query.isPresent()) {
            var posts = postService.search(query.get(), null);
            model.addAttribute("posts", posts);
        }
        return "search/layout";
    }
}
