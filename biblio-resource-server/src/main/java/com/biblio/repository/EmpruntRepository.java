/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.repository;

import com.biblio.entity.Emprunt;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kouwonou
 */
public interface EmpruntRepository  extends JpaRepository<Emprunt, Long>{
  public Optional<Emprunt> findByReference(String reference) ;
  
}
