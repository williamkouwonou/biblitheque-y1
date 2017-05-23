/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author kouwonou
 */
@Configuration
public class ExternalTomcatConfig extends SpringBootServletInitializer {
     @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder bulder) {
        return bulder.sources(MainAdmin.class);
    }

}
