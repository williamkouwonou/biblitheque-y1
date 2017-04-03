/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.repository;

import com.biblio.entity.Categorie;
import com.biblio.entity.Livre;
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
public interface LivreRepository extends JpaRepository<Livre, Long> {

    public Optional<Livre> findOneByIsbn(String isbn);

    public List<Livre> findByCategorie(Categorie c);

    public Page<Livre> findByCategorie(Categorie c, Pageable p);

    @Query("SELECT l FROM Livre l WHERE l.categorie.id =:id")
    public List<Livre> findByCategorie(@Param("id") Long IdCategorie);

    @Query("SELECT l FROM Livre l WHERE l.categorie.id =:id")
    public Page<Livre> findByCategorie(@Param("id") Long IdCategorie, Pageable p);

    @Query("SELECT l FROM Livre l WHERE l.titre LIKE :motCle OR l.auteurs LIKE :motCle OR l.resume LIKE :motCle OR l.categorie.designation LIKE :motCle  OR l.categorie.description LIKE :motCle")
    public List<Livre> findByMotCle(@Param("motCle")String motCle);
    @Query("SELECT l FROM Livre l WHERE l.titre LIKE :motCle OR l.auteurs LIKE :motCle OR l.resume LIKE :motCle OR l.categorie.designation LIKE :motCle  OR l.categorie.description LIKE :motCle")
    public Page<Livre> findByMotCle(@Param("motCle")String motCle,Pageable p);
}
