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
    public String mainSearchPage(@RequestParam Optional<String> query, @RequestParam Optional<String[]> categories, Model model) {
        if (query.isEmpty()) {
            return "search/layout";
        }

        StringBuilder queryString = new StringBuilder(query.get());

        if (categories.isPresent()) {
            for (var category : categories.get()) {
                queryString.append("$").append(category).append(" ");
            }
        }

        var posts = postService.search(queryString.toString());
        model.addAttribute("posts", posts);

        return "search/layout";
    }
}
