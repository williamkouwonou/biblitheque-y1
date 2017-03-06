/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.security;

import com.biblio.entity.Role;
import com.biblio.entity.User;
import com.biblio.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author sewa
 */
@Component
public class BaseAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private UserRepository userRepository;

    @Inject
    private HttpServletRequest request;

    @Inject
    private PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


       
            String username = authentication.getName();
            String password = (String) authentication.getCredentials();

            System.out.println("Username "+username);
            System.out.println("Password "+password);
            User user = userRepository.findByLogin(username);
            
            System.out.println("GGG "+user);
            if (user == null || !user.getLogin().equalsIgnoreCase(username)) {
                throw new BadCredentialsException("unknown user");
            }

            if (!encoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("unknown user");
            }

            if (!user.getActivated()) {
                throw new UserNotActivatedException(user.getLogin().toLowerCase(Locale.FRENCH));
            }       
        

        return new UsernamePasswordAuthenticationToken(user, password, toGrantedAuthorities(user));
    }

    private static Collection<GrantedAuthority> toGrantedAuthorities(final User user) {
        final Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoles().stream().forEach((role) -> {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        });
        return grantedAuthorities;
    }

    private Map<String, String> getHeadersInfo() {

        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
