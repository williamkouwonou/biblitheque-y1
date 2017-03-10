/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.config;

import com.biblio.entity.Categorie;
import com.biblio.entity.Role;
import com.biblio.entity.User;
import com.biblio.repository.RoleRepository;
import com.biblio.repository.UserRepository;
import com.biblio.security.util.ConstantRole;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
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
    public JavaMailSenderImpl javaMailSenderImpl() {
        return new JavaMailSenderImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "initRoles")
    public Role initRoles() {
        LOG.info("INITIALISATION DES ROLE");

        if (roleRepository.findByName(ConstantRole.ADMIN_ROLE) == null) {
            roleRepository.save(new Role(ConstantRole.ADMIN_ROLE));
        }
        if (roleRepository.findByName(ConstantRole.USER_ROLE) == null) {
            roleRepository.save(new Role(ConstantRole.USER_ROLE));
        }
        return new Role();
    }

    @Bean
    @DependsOn("initRoles")
    public User addUser() {
        LOG.info("INITIALISATION DES UTILISTEUR :");
        User u = new User();
        u.setCreatedBy("SYSTEM");
        u.setActivated(true);
        u.setDateNaissance(new Date());
        u.setNom("Admin");
        u.setLogin("admin");
        u.setPrenom("admin");
        u.setEmail("admin@gmail.com");
        u.setPassword(passwordEncoder().encode("admin"));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ConstantRole.ADMIN_ROLE));
        roles.add(roleRepository.findByName(ConstantRole.USER_ROLE));
        u.setRoles(roles);
        User u1 = userRepository.findByLogin("admin");
        if (u1 == null || !u.equals(u1)) {
            userRepository.save(u);
        }
        return u;
    }
    private static final Logger LOG = Logger.getLogger(Beans.class.getName());

}
