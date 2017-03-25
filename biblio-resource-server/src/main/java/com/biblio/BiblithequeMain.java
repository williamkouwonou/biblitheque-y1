/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio;

import com.biblio.entity.User;
import com.biblio.repository.UserRepository;
import com.biblio.security.SecurityUtils;
import com.biblio.service.util.RandomUtil;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alindaessi
 */
@SpringBootApplication
@RestController
public class BiblithequeMain {

    @Inject
    UserRepository userRepository;
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
        
        return user;
    }
    @RequestMapping(value = "/user/info")
    public Object userInfo() {
        Map<String, Object> modele = new HashMap<>();
        String login =SecurityUtils.getCurrentUserLogin();
        User u = userRepository.findByLogin(login);
        
        if(u!=null){
            
            System.out.println("uu "+u.getNom()+"  "+u.getPrenom());
            modele.put("name", u.getNom()+" "+u.getPrenom());
            modele.put("nom", u.getNom());
            modele.put("prenom", u.getPrenom());
            modele.put("dateNaissance", u.getDateNaissance());
            modele.put("login", u.getLogin());
            modele.put("email", u.getEmail());
            modele.put("tel", u.getTel());
            modele.put("id", u.getId());
        }
        return modele;
    }
     @RequestMapping(value = "/logout2", method = RequestMethod.GET)
    public Object logout1() {
         Map<String, Object> modele = new HashMap<>();
                  System.out.println("HHHHHHHHHHHHHHHHHHHHhh");

         modele.put("nom", "william");
         return modele;
                 
    }
}
