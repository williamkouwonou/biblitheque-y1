/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.repository;

import com.biblio.entity.Emprunt;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kouwonou
 */
public interface EmpruntRepository  extends JpaRepository<Emprunt, Long>{
  public Optional<Emprunt> findByReference(String reference) ;
  public Optional<Emprunt> findByMembre(String reference) ;
  
  @Query("SELECT COUNT(e) FROM Emprunt e WHERE e.livre.isbn=:isbn AND e.dateEmprunt>=:dd AND e.dateEmprunt<=:df")
  public Long countByIsbn(@Param("isbn")String isbn,@Param("dd")Date dd,@Param("df")Date df);
  @Query("SELECT COUNT(e) FROM Emprunt e WHERE e.livre.categorie.id=:idc AND e.dateEmprunt>=:dd AND e.dateEmprunt<=:df")
  public Long countByCategorie(@Param("idc")String isbn,@Param("dd")Date dd,@Param("df")Date df);
  /**
   * 
   * @param motCle mot cle
   * @param dd date debut
   * @param df date Fin
   * @return  liste des emprunt
   */
  @Query("SELECT l FROM Emprunt l WHERE (l.membre.login LIKE :motCle OR l.membre.nom LIKE  :motCle OR"
          + " l.membre.prenom LIKE :motCle OR "
          + "l.membre.tel LIKE :motCle OR l.membre.email LIKE :motCle OR "
          + " l.livre.titre LIKE :motCle OR l.livre.auteurs LIKE :motCle OR "
          + "l.livre.resume LIKE :motCle OR l.livre.categorie.designation LIKE :motCle "
          + " OR l.livre.categorie.description LIKE :motCle) AND l.dateEmprunt>=:dd AND l.dateEmprunt<=:df")
  public List<Emprunt> findByDateMoTCleAndDateEmprunt(@Param("motCle")String motCle,@Param("dd")Date dd,@Param("df")Date df);
  /**
   * 
   * @param motCle mot cle
   * @param dd date debut
   * @param df date Fin
     * @param p
   * @return  liste des emprunt
   */
  @Query("SELECT l FROM Emprunt l WHERE (l.membre.login LIKE :motCle OR l.membre.nom LIKE  :motCle OR"
          + " l.membre.prenom LIKE :motCle OR "
          + "l.membre.tel LIKE :motCle OR l.membre.email LIKE :motCle OR "
          + " l.livre.titre LIKE :motCle OR l.livre.auteurs LIKE :motCle OR "
          + "l.livre.resume LIKE :motCle OR l.livre.categorie.designation LIKE :motCle "
          + " OR l.livre.categorie.description LIKE :motCle) AND l.dateEmprunt>=:dd AND l.dateEmprunt<=:df")
  public Page<Emprunt> findByDateMoTCleAndDateEmprunt(@Param("motCle")String motCle,@Param("dd")Date dd,@Param("df")Date df,Pageable p);
  
}
