/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.web.rest;

import com.biblio.entity.Categorie;
import com.biblio.entity.Livre;
import com.biblio.repository.CategorieRepository;
import com.biblio.repository.LivreRepository;
import com.biblio.user.module.utils.Constants;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kouwonou
 */
@RestController
@RequestMapping(value = "/api/livres")
public class LivreResource {

    private final Logger log = LoggerFactory.getLogger(LivreResource.class);

    @Inject
    private LivreRepository livreRepository;
    @Inject
    CategorieRepository categorieRepository;

    /**
     * POST /livres : Create a new livre.
     *
     * @param livre the livre to create
     * @param idcategorie
     * @param bindingResult
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new livre, or with status 400 (Bad Request) if the livre has already an
     * ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/create/{idc}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object createLivre(@Valid @RequestBody Livre livre, @PathVariable("idc") Long idcategorie, BindingResult bindingResult) throws URISyntaxException {
        Map<String, Object> modele = new HashMap<>();
        if (bindingResult.hasErrors()) {

            modele.put(Constants.ERROR, true);
            modele.put(Constants.MESSAGE, "Enregistrement échoué");
            bindingResult.getFieldErrors().stream().forEach((f) -> {
                modele.put(f.getField(), f.getDefaultMessage());
            });

            return modele;
        }
        if (livre.getId() != null) {
            modele.put(Constants.ERROR, true);
            modele.put(Constants.MESSAGE, "Enregistrement échoué");
            modele.put("id", "Un nouveau livre ne peut pas etre créé avec un ID");
            return modele;
        }
        if (livreRepository.findOneByIsbn(livre.getIsbn()).isPresent()) {
            modele.put(Constants.ERROR, true);
            modele.put(Constants.MESSAGE, "Enregistrement échoué");
            modele.put("isbn", "Cet isbn existe deja");
            return modele;
        }
        Categorie categorie = categorieRepository.findOne(idcategorie);

        if (categorie == null) {
            modele.put(Constants.ERROR, true);
            modele.put(Constants.MESSAGE, "Enregistrement échoué");
            modele.put("categorie", "Cette  categorie existe pas");
            return modele;
        }
        livre.setCategorie(categorie);
        Livre result = livreRepository.save(livre);
        log.debug("REST request to save Livre : {}", livre);
        modele.put(Constants.MESSAGE, "Enregistrement réussi");
        return modele;
    }

    /**
     * PUT /livres : Updates an existing livre.
     *
     * @param livre the livre to update
     * @param idcategorie
     * @param bindingResult
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * livre, or with status 400 (Bad Request) if the livre is not valid, or
     * with status 500 (Internal Server Error) if the livre couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/update/{idc}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateLivre(@Valid @RequestBody Livre livre, @PathVariable("idc") Long idcategorie, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to update Livre : {}", livre);
        Map<String, Object> modele = new HashMap<>();
        if (livre.getId() == null) {
            return createLivre(livre, idcategorie, bindingResult);
        }
        if (bindingResult.hasErrors()) {

            modele.put(Constants.ERROR, true);
            modele.put(Constants.MESSAGE, "Enregistrement échoué");
            bindingResult.getFieldErrors().stream().forEach((f) -> {
                modele.put(f.getField(), f.getDefaultMessage());
            });

            return modele;
        }
        Categorie categorie = categorieRepository.findOne(idcategorie);

        if (categorie == null) {
            modele.put(Constants.ERROR, true);
            modele.put(Constants.MESSAGE, "Enregistrement échoué");
            modele.put("categorie", "Cette  categorie existe pas");
            return modele;
        }
        livre.setCategorie(categorie);
        Livre result = livreRepository.saveAndFlush(livre);
        log.debug(" Update  Livre : {}", livre);
        modele.put(Constants.MESSAGE, "Enregistrement réussi");
        return modele;
    }

    /**
     * GET /livres : get all the livres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of livres in
     * body
     */
    @RequestMapping(value = "/listall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Livre> getAllLivres() {
        log.debug("REST request to get all Livres");
        List<Livre> livres = livreRepository.findAll();
        return livres;
    }

    /**
     * GET /livres/:id : get the "id" livre.
     *
     * @param id the id of the livre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the livre,
     * or with status 404 (Not Found)
     */
    @RequestMapping(value = "/getone/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getLivre(@PathVariable Long id) {
        log.debug("REST request to get Livre : {}", id);
        Livre livre = livreRepository.findOne(id);
        Map<String, Object> modele = new HashMap<>();

        if (livre == null) {
            modele.put(Constants.MESSAGE, "Le livre avec l'ID " + id + " n'existe pas");
            modele.put(Constants.ERROR, true);
            return modele;
        }

        return livre;
    }

    /**
     * DELETE /livres/:id : delete the "id" livre.
     *
     * @param id the id of the livre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteLivre(@PathVariable Long id) {
        log.debug("REST request to delete Livre : {}", id);
        livreRepository.delete(id);

        log.debug("REST request to delete Categorie : {}", id);

        Map<String, Object> modele = new HashMap<>();
        modele.put(Constants.MESSAGE, "Enregistrement réussi");
        return modele;
    }

    /**
     * GET /categories : get all the categories.
     *
     * @param mc
     * @return the ResponseEntity with status 200 (OK) and the list of
     * categories in body
     */
    @RequestMapping(value = "/searche", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Livre> getALLByMoCle(String mc) {
        log.debug("REST request to get all Categories");
        List<Livre> livres = livreRepository.findByMotCle(mc);
        return livres;
    }

    /**
     * GET /categories : get all the categories.
     *
     * @param mc
     * @param size
     * @param page
     * @return the ResponseEntity with status 200 (OK) and the list of
     * categories in body
     */
    @RequestMapping(value = "/searchepage/{size}/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getALLByMoCle(@RequestParam("mc") String mc, @PathVariable("size") int size, @PathVariable("page") int page) {
        System.out.println("HHHHHHHHHH");
        log.debug("REST request to get all Categories");
        if (mc == null) {
            mc = "";
        }
        mc = "%" + mc + "%";
        
        System.out.println("MC "+mc);
        System.out.println("size "+size);
        System.out.println("page "+page);
        Page<Livre> livres = livreRepository.findByMotCle(mc, new PageRequest(page, size));
        
        System.out.println("NNN "+livres.getContent());
        return livres;
    }
}
