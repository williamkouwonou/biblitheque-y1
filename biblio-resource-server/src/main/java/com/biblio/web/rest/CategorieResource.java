/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.web.rest;

import com.biblio.entity.Categorie;
import com.biblio.repository.CategorieRepository;
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
@RequestMapping(value = "/api/categories")
public class CategorieResource {

    private final Logger log = LoggerFactory.getLogger(CategorieResource.class);

    @Inject
    private CategorieRepository categorieRepository;

    /**
     * POST /categories : Create a new compte.
     *
     * @param categorie
     * @param bindingResult
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new compte, or with status 400 (Bad Request) if the compte has already an
     * ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object createCategorie(@Valid @RequestBody Categorie categorie, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to save Categorie : {}", categorie);
        Map<String, Object> modele = new HashMap<>();

        if (bindingResult.hasErrors()) {

            modele.put(Constants.ERROR, true);
            modele.put(Constants.MESSAGE, "Enregistrement échoué");
            bindingResult.getFieldErrors().stream().forEach((f) -> {
                modele.put(f.getField(), f.getDefaultMessage());
            });

            return modele;
        }
        if (categorie.getId() != null) {
            modele.put(Constants.ERROR, true);
            modele.put(Constants.MESSAGE, "Enregistrement échoué");
            modele.put("id", "Un nouveau categorie ne peut pas etre créé avec un ID");
            return modele;
        }
        if (categorieRepository.findByDesignation(categorie.getDesignation()).isPresent()) {
            modele.put(Constants.ERROR, true);
            modele.put(Constants.MESSAGE, "Enregistrement échoué");
            modele.put("designation", "Cette Designation existe deja");
            return modele;
        }
        Categorie result = categorieRepository.save(categorie);
        log.debug("REST request to save Categorie : {}", categorie);
        modele.put(Constants.MESSAGE, "Enregistrement réussi");
        return modele;
    }

    /**
     * PUT /categories : Updates an existing compte.
     *
     * @param categorie
     * @param bindingResult
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * compte, or with status 400 (Bad Request) if the compte is not valid, or
     * with status 500 (Internal Server Error) if the compte couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateCategorie(@Valid @RequestBody Categorie categorie, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to update Categorie : {}", categorie);
        Map<String, Object> modele = new HashMap<>();
        if (categorie.getId() == null) {
            return createCategorie(categorie, bindingResult);
        }
        if (bindingResult.hasErrors()) {

            modele.put(Constants.ERROR, true);
            modele.put(Constants.MESSAGE, "Enregistrement échoué");
            bindingResult.getFieldErrors().stream().forEach((f) -> {
                modele.put(f.getField(), f.getDefaultMessage());
            });

            return modele;
        }
        Categorie result = categorieRepository.saveAndFlush(categorie);
        log.debug(" Update  Categorie : {}", categorie);
        modele.put(Constants.MESSAGE, "Enregistrement réussi");
        return modele;
    }

    /**
     * GET /categories : get all the categories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * categories in body
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Categorie> getAllCategories() {
        log.debug("REST request to get all Categories");
        List<Categorie> categories = categorieRepository.findAll();
        return categories;
    }

    /**
     * GET /categories : get all the categories.
     *
     * @param mc
     * @return the ResponseEntity with status 200 (OK) and the list of
     * categories in body
     */
    @RequestMapping(value = "/searche", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Categorie> getALLByMoCle(String mc) {
        if (mc == null) {
            mc = "";
        }
        mc = "%" + mc + "%";
        log.debug("REST request to get all Categories");
        List<Categorie> categories = categorieRepository.findByMotCle(mc);
        return categories;
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
        log.debug("REST request to get all Categories");

        if (mc == null) {
            mc = "";
        }
        mc = "%" + mc + "%";
        Page<Categorie> categories = categorieRepository.findByMotCle(mc, new PageRequest(page, size));
        return categories;
    }

    /**
     * GET /categories/:id : get the "id" compte.
     *
     * @param id the id of the compte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compte,
     * or with status 404 (Not Found)
     */
    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getCategorie(@PathVariable Long id) {
        log.debug("REST request to get Categorie : {}", id);
        Categorie categorie = categorieRepository.findOne(id);
        Map<String, Object> modele = new HashMap<>();

        if (categorie == null) {
            modele.put(Constants.MESSAGE, "La categorie avec l'ID " + id + " n'existe pas");
            modele.put(Constants.ERROR, true);
            return modele;
        }

        return categorie;
    }

    /**
     * DELETE /categories/:id : delete the "id" compte.
     *
     * @param id the id of the compte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteCategorie(@PathVariable Long id) {

        log.debug("REST request to delete Categorie : {}", id);
        categorieRepository.delete(id);
        Map<String, Object> modele = new HashMap<>();
        modele.put(Constants.MESSAGE, "Enregistrement réussi");
        return modele;
    }

}
