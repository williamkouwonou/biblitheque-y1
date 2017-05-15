/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
@RestController
public class MainAdmin extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MainAdmin.class, args);
    }

        @RequestMapping(value = "/auth/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public Object logoutUrl() {
        Map<String, Object> modele = new HashMap<>();
        modele.put("url", env.getProperty("server.oauth2.logout"));
        return modele;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http//sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                .antMatchers("/index.html", "/home.html", "/","/auth/logout", "/login", "/app/**", "/font-awesome/**", "/appjs/**", "/layout/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().logout()
                //.logoutUrl("http://localhost:9070/authserver/logout");
                .logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                //.logoutSuccessHandler(customLogoutSuccessHandler)
                .deleteCookies("JSESSIONID");
    }

}
