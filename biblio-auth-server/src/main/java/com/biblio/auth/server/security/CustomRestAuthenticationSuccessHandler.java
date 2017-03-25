package com.biblio.auth.server.security;



import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
public class CustomRestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        if (authentication != null) {
           
            CustomSecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, authentication.getPrincipal());
        }
    }
}
