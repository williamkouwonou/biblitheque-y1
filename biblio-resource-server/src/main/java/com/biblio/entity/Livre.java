/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author alindaessi
 *
 * Entité représentant les informations d'un livre
 */
@Entity
public class Livre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * le titre du livre
     */
    @NotNull
    @Size(min = 2)
    @Column(length = 250, nullable = false)
    private String titre;
    /**
     * les auteurs du livre separé par des ,
     */
    @NotNull
    @Size(min = 2)
    @Column(nullable = false)
    private String auteurs;
    /**
     * éditeur du livre
     */
    private String editeur;

    private Integer edition;
    /**
     * collection
     */
    private String collection;
    /**
     * Résumé du livre
     */
    private String resume;
    /**
     * date de parution du livre
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateParution;

    /**
     * code barre
     */
    @NotNull
    @Size(min = 2)
    @Column(nullable = false)
    private String isbn;
    /**
     * catégorie de livre
     */
    @ManyToOne
    private Categorie categorie;

    /**
     * quantité de livre restant
     */
    private Integer quantite = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(String auteurs) {
        this.auteurs = auteurs;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Date getDateParution() {
        return dateParution;
    }

    public void setDateParution(Date dateParution) {
        this.dateParution = dateParution;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

}
