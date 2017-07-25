/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.web.rest;

import java.util.Date;
import javax.persistence.Temporal;

/**
 *
 * @author kouwonou
 */
public class SearcheParameters {
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateDebut; //date des emprunts >= cette date (optionnel)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateFin;//date des emprunts <= cette date (optionnel)
    private Integer size; // le nombre d'element de la liste (optionnel)
    private Integer page; // la page (optionnel)
    private String moCle; // le mot clé recherché (optionnel)

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getMoCle() {
        return moCle;
    }

    public void setMoCle(String moCle) {
        this.moCle = moCle;
    }

}
