package com.biblio.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.security.core.userdetails.User;

/**
 * Utility class for Spring Security.
 */
public final class CustomSecurityUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    private CustomSecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return
     */
    public static String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails springSecurityUser = null;
        String userName = null;

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
                System.out.println("+++++++++++++++++++++++++++++++ "+userName);
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }

        return userName;
    }
    
    public static String getPositionTrackerCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User springSecurityUser = null;
        String userName = null;
        springSecurityUser = (User) authentication.getPrincipal();
        userName = springSecurityUser.getUsername();
        return userName;
    }
    
    public static String getMobileMarchandCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User springSecurityUser = null;
        String userName = null;
        springSecurityUser = (User) authentication.getPrincipal();
        userName = springSecurityUser.getUsername();
        return userName;
    }
    
    public static String getAgentTransfertCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User springSecurityUser = null;
        String userName = null;
        springSecurityUser = (User) authentication.getPrincipal();
        userName = springSecurityUser.getUsername();
        return userName;
    }
    

    public static void sendError(HttpServletResponse response, Exception exception, int status, String message) throws IOException {
        System.out.println("********************** ERROR");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        CustomError error = new CustomError("authError", exception.getMessage());
        writer.write(mapper.writeValueAsString(new CustomResponse(status, message, error)));
        writer.flush();
        writer.close();
    }

    public static void sendResponse(HttpServletResponse response, int status, Object object) throws IOException {
         System.out.println("---------------------------Response OK");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(mapper.writeValueAsString(object));
        response.setStatus(status);
        writer.flush();
        writer.close();
    }

}
