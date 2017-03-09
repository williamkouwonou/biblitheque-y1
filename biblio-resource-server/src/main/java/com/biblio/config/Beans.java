/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.config;

import com.biblio.entity.Role;
import com.biblio.entity.User;
import com.biblio.repository.RoleRepository;
import com.biblio.repository.UserRepository;
import com.biblio.security.util.ConstantRole;
import java.util.Date;
import javax.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author kouwonou
 */
@Configuration
public class Beans {
    @Inject
    private RoleRepository roleRepository;
    @Inject
    private UserRepository userRepository;
    @Bean
    public JavaMailSenderImpl javaMailSenderImpl(){
        return new JavaMailSenderImpl();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    
    @Bean
    public Role initRoles(){
        if(roleRepository.findByName(ConstantRole.ADMIN_ROLE)==null){
            roleRepository.save(new Role(ConstantRole.ADMIN_ROLE));
        }
        if(roleRepository.findByName(ConstantRole.USER_ROLE)==null){
            roleRepository.save(new Role(ConstantRole.USER_ROLE));
        }
        return new Role();
    }
    
    @Bean
    public User addUser(){
        User u= new User();
        u.setCreatedBy("SYSTEM");
        u.setActivated(true);
        u.setDateNaissance(new Date());
        u.setNom("Admin");
        u.setLogin("admin");
        u.setPrenom("admin");
        u.setEmail("admin@gmail.com");
        u.setPassword(passwordEncoder().encode("admin"));
        if(userRepository.findByLogin("admin")==null){
            userRepository.save(u);
        }
        return u;
    }
}
