/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.auth.server.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author kouwonou
 */
public class CustomFilter extends GenericFilterBean {

    @Override
    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
       
         HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
         System.out.println("XXXXXXXXXXXXXXXXX  "+request.getRequestURL());
        HttpSession httpSession =request.getSession();
        if(request.getRequestURL().toString().contains("token")){
            Cookie[] c = request.getCookies();
            
            if(c!=null){
                for(Cookie k: c){
                 System.out.println("k.getValue() : "+k.getValue());
                   
                if(k.getName().equalsIgnoreCase("JSESSIONID")){
                    System.out.println("k.getValue() : "+k.getValue());
                    System.out.println("httpSession.getId() : "+httpSession.getId());
                    k.setValue(httpSession.getId());
                }
            }
            }
        }
        chain.doFilter(request, response);
    }
}
