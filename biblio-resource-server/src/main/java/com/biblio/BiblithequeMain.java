/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio;

import com.biblio.service.util.RandomUtil;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kouwonou
 */
@SpringBootApplication
@RestController
public class BiblithequeMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SpringApplication.run(BiblithequeMain.class, args);
    }

    @RequestMapping(value = "/hello")
    public Object hello() {
        Map<String, Object> modele = new HashMap<>();
        modele.put("content", "Bonjour , Tu viens de te connecter au serveur resource");
       modele.put("id", RandomUtil.generateActivationKey());
        return modele;
    }
    @RequestMapping(value = "/user")
    public Object hello(Principal user) {
        System.out.println("HHH");
        return user;
    }
    
}
