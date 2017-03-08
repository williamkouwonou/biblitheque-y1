/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio;

import java.security.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author kouwonou
 */
@SpringBootApplication
@RestController
public class BiblithequeMain  {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SpringApplication.run(BiblithequeMain.class, args);
    }

   
    @RequestMapping(value = "hello")
    public String hello(){
        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHh");
        return "Bonjour";
    }
}
