/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 *
 * @author kouwonou
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(-20)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 @Autowired
    private CustomRestUnauthorizedEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private BaseAuthenticationProvider baseAuthenticationProvider;

    
    @Autowired
    private AccessDeniedHandler restAccessDeniedHandler;
//
//    @Autowired
//    private AuthenticationSuccessHandler restAuthenticationSuccessHandler;
    @Autowired
    private CustomRestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler restAuthenticationFailureHandler;
//    @Autowired
//    private BaseAuthenticationProvider baseAuthenticationProvider;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(baseAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
                .headers().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                
                
                .antMatchers("/api/register/**").hasRole("USER")
                
                //.antMatchers("/api/transactionnonvalidee/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(restAccessDeniedHandler)
                .and()
                .formLogin()
                .loginProcessingUrl("/authenticate").permitAll()
                .successHandler(restAuthenticationSuccessHandler)
                .failureHandler(restAuthenticationFailureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .permitAll().and().httpBasic();
    }

//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.parentAuthenticationManager(authenticationManager);
//        }
}
