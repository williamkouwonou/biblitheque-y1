/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.auth.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.session.SessionManagementFilter;

/**
 *
 * @author alindaessi
 */
@Configuration
@Order(-20)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SimpleCorsFilter simpleCorsFilter;
    @Autowired
    private CustomRestUnauthorizedEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private BaseAuthenticationProvider baseAuthenticationProvider;

    @Autowired
    private AccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private CustomRestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler restAuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(baseAuthenticationProvider);
        //auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
       
        //http.sessionManagement().invalidSessionStrategy(invalidSessionStrategy)
        http.addFilterAfter(
                new CustomFilter(),SessionManagementFilter.class)
                .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/api/register/**").hasRole("ADMIN")
                .antMatchers("/logout2/**").permitAll()
                .and().formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutUrl("/logout2").invalidateHttpSession(true).clearAuthentication(true)
                .permitAll();
        // @formatter:on
    }
}
