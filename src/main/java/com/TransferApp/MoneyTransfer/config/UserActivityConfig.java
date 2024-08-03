package com.TransferApp.MoneyTransfer.config;

import com.TransferApp.MoneyTransfer.logging.UserActivityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserActivityConfig {

    @Bean(name = "customUserActivityFilter")
    public FilterRegistrationBean<UserActivityFilter> userActivityFilter() {
        FilterRegistrationBean<UserActivityFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserActivityFilter());
        registrationBean.addUrlPatterns("/*"); // Apply to all URLs or specific patterns
        registrationBean.setOrder(1); // Set the order of the filter
        return registrationBean;
    }
}
