/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.entity;

import com.biblio.service.util.RandomUtil;
import com.biblio.user.module.entity.User;
import com.biblio.web.rest.util.Utils;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;

@Entity
public class Emprunt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String reference;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEmprunt;
    @ManyToOne
    private User membre;
    @ManyToOne
    private Livre livre;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeRendu;

    /**
     * oui si le livre est retourné , NON si non.
     */
    private Boolean etat= Boolean.FALSE;
    public Emprunt() {
    }

    public Emprunt(User membre, Livre livre, Date dateDeRendu) {
        this.membre = membre;
        this.livre = livre;
        this.dateDeRendu = dateDeRendu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
    public void setDateEmprunt(String dateEmprunt) throws ParseException {
        this.dateEmprunt = Utils.convertToDate(dateEmprunt);
    }

    public User getMembre() {
        return membre;
    }

    public void setMembre(User membre) {
        this.membre = membre;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Date getDateDeRendu() {
        return dateDeRendu;
    }

    public void setDateDeRendu(Date dateDeRendu) {
        this.dateDeRendu = dateDeRendu;
    }
    public void setDateDeRendu(String dateDeRendu) throws ParseException {
        this.dateDeRendu = Utils.convertToDate(dateDeRendu);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Emprunt other = (Emprunt) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Emprunt{" + "id=" + id + ", reference=" + reference + ", dateEmprunt=" + dateEmprunt + ", membre=" + membre + ", livre=" + livre + ", dateDeRendu=" + dateDeRendu + '}';
    }
    private static final Logger LOG = Logger.getLogger(Emprunt.class.getName());
   
    @PrePersist
    public void init(){
        etat=false;
        dateEmprunt= new Date();
        reference = RandomUtil.getRref();
    }
}
