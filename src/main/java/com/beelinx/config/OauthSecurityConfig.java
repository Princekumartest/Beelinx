package com.beelinx.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class OauthSecurityConfig {

        static Logger logger = LoggerFactory.getLogger(OauthSecurityConfig.class);

        @Autowired
       private OAuthAuthenicationSuccessHandler successHandler;


    @Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Inside securityFilterChain");
        http

                .oauth2Login(oauth ->
                        oauth.successHandler(successHandler())  // Use your custom success handler
                );

            return http.build();
        }



    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new OAuthAuthenicationSuccessHandler();
    }

    @Bean
    @Primary
    public RedirectStrategy redirectStrategy() {
        return new DefaultRedirectStrategy();
    }

}

