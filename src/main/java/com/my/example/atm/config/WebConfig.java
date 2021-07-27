package com.my.example.atm.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

// TODO: Delete this class if unused in the future
//@Configuration
public class WebConfig {

//    @Bean
    public FilterRegistrationBean<SessionFilter> filter(){
        FilterRegistrationBean<SessionFilter> filter = new FilterRegistrationBean<>();
        SessionFilter customFilter = new SessionFilter();

        filter.setFilter(customFilter);
        filter.addUrlPatterns("/app/*");

        return filter;
    }
}
