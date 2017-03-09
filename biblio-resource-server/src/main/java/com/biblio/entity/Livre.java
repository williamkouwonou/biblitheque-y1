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
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author alindaessi
 *
 * Entité représentant les informations d'un livre
 */
@Entity
public class Livre implements Serializable {

    @Id
    private Long id;
    /**
     * le titre du livre
     */
    @Column(length = 250, nullable = false)
    private String titre;
    /**
     * les auteurs du livre separé par des ,
     */
    @Column(nullable = false)
    private String auteurs;
    /**
     * éditeur du livre
     */
    private String editeur;
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
    private int quantite = 0;

    public Livre() {
    }

    public Livre(String titre, String auteurs, String editeur, String collection, String resume, Date dateParution, String isbn, Categorie categorie) {
        this.titre = titre;
        this.auteurs = auteurs;
        this.editeur = editeur;
        this.collection = collection;
        this.resume = resume;
        this.dateParution = dateParution;
        this.isbn = isbn;
        this.categorie = categorie;
    }

    public Livre(String titre, String auteurs, String editeur, String collection, String resume, Date dateParution, String isbn) {
        this.titre = titre;
        this.auteurs = auteurs;
        this.editeur = editeur;
        this.collection = collection;
        this.resume = resume;
        this.dateParution = dateParution;
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
