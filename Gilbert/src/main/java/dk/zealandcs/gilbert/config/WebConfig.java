package dk.zealandcs.gilbert.config;

import dk.zealandcs.gilbert.presentation.profile.ProfileInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final ProfileInterceptor profileInterceptor;

    public WebConfig(ProfileInterceptor profileInterceptor) {
        this.profileInterceptor = profileInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(profileInterceptor).addPathPatterns("/profile/@{username}/**");
    }
}
