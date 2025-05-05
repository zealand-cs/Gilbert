package dk.zealandcs.gilbert.presentation;

import dk.zealandcs.gilbert.application.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final IUserService userService;

    HomeController(IUserService userService) { this.userService = userService; }

    @GetMapping
    public String home() {
        return "home/index";
    }
}
