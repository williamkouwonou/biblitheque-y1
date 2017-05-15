/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alindaessi
 */
@SpringBootApplication
@EnableResourceServer
@RestController
public class MainAuthServer extends SpringBootServletInitializer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MainAuthServer.class, args);
    }

    

   // @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String  logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
         return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

  
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder bulder) {
        return bulder.sources(MainAuthServer.class);
    }
}
