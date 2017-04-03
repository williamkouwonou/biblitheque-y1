/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.repository;

import com.biblio.entity.Categorie;
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
public interface CategorieRepository extends JpaRepository<Categorie, Long>{ 
    public Optional<Categorie> findByDesignation(String desgnation);
    @Query("SELECT c FROM Categorie c WHERE c.designation LIKE :mc OR c.description LIKE :mc")
    public List<Categorie> findByMotCle(@Param("mc")String moc);
    @Query("SELECT c FROM Categorie c WHERE c.designation LIKE :mc OR c.description LIKE :mc")
    public Page<Categorie> findByMotCle(@Param("mc")String moc,Pageable p);
}
