/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
public class MainAdmin extends WebSecurityConfigurerAdapter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MainAdmin.class, args);
    }
    @Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.logout().and()
			.authorizeRequests()
				.antMatchers("/index.html", "/home.html", "/", "/login").permitAll()
				.anyRequest().authenticated()
				.and()
			.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
			// @formatter:on
	}
}
