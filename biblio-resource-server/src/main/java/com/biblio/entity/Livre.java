/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.entity;

import java.util.Date;

/**
 *
 * @author alindaessi
 * 
 * Entité représentant les informations d'un livre
 */

public class Livre {
    private Long id;
    /**
     * le titre  du livre
     */
    private String titre;
    /**
     * les auteurs du livre separé par des ,
     */
    private String auteurs;
    
    /**
     * Résumé du livre
     */
    private String resume;
    /**
     * date de parution du livre
     */
    private Date dateParution;
    
    /**
     * code barre
     */
    private String isbn;
}
