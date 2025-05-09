package dk.zealandcs.gilbert.config;

import dk.zealandcs.gilbert.config.filters.AuthFilter;
import dk.zealandcs.gilbert.config.filters.LoggedInFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<AuthFilter> authenticateFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthFilter());
        registrationBean.addUrlPatterns("/me/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<LoggedInFilter> loggedInFilter() {
        FilterRegistrationBean<LoggedInFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LoggedInFilter());
        registrationBean.addUrlPatterns("/auth/*");

        return registrationBean;
    }
}
