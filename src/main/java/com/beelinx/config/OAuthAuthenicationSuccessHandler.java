package com.beelinx.config;

import com.beelinx.model.OAuthEntity;
import com.beelinx.repository.jpa.OAuthRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class OAuthAuthenicationSuccessHandler  implements AuthenticationSuccessHandler {

    static Logger log = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);

    @Autowired
    private OAuthRepository oAuthRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       log.info("Inside onAuthenticationSuccess");
        var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key, value) -> {
            log.info(key + " : " + value);
        });
        log.info("Authorities",oauthUser.getAuthorities().toString());

        OAuthEntity user=new OAuthEntity();
        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            // google attributes
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setUniqueId(oauthUser.getAttribute("sub").toString());
        }
         String email = oauthUser.getAttribute("email").toString();
          OAuthEntity user2= oAuthRepository.findByEmail(email);
        if (user2 == null) {
            oAuthRepository.save(user);
            log.info("User is login successfully");
        }

       new DefaultRedirectStrategy().sendRedirect(request, response, "/home");
    }
}

