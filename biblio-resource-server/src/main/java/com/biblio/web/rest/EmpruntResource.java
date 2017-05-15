/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.web.rest;

import com.biblio.entity.Emprunt;
import com.biblio.entity.Livre;
import com.biblio.repository.EmpruntRepository;
import com.biblio.repository.LivreRepository;
import com.biblio.security.SecurityUtils;
import com.biblio.service.MailService;
import com.biblio.user.module.entity.User;
import com.biblio.user.module.repository.UserRepository;
import com.biblio.user.module.utils.Constants;
import com.biblio.user.module.utils.Profile;
import com.biblio.web.rest.util.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kouwonou
 */
@RestController
@RequestMapping(value = "/api/emprunts")
public class EmpruntResource {

    private final Logger log = LoggerFactory.getLogger(EmpruntResource.class);
    @Inject
    private LivreRepository livreRepository;
    @Inject
    private EmpruntRepository empruntRepository;

    @Inject
    private UserRepository userRepository;
    @Inject
    private MailService mailService;

    /**
     * GET /livres/:id : get the "id" livre.
     *
     * @param isbn
     * @return the ResponseEntity with status 200 (OK) and with body the livre,
     * or with status 404 (Not Found)
     * @throws java.text.ParseException
     * @throws javax.mail.MessagingException
     */
    @RequestMapping(value = "createmobile/{isbn}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object emprunter(@PathVariable String isbn) throws ParseException, MessagingException {
        log.debug("REST request to emprunter  Livre : {}", isbn);
       
        
        User u = userRepository.findByLogin(SecurityUtils.getCurrentUserLogin());

        return emprunter(isbn, u.getLogin());
    }

    /**
     * GET /livres/:id : get the "id" livre.
     *
     * @param isbn
     * @param login
     * @return the ResponseEntity with status 200 (OK) and with body the livre,
     * or with status 404 (Not Found)
     * @throws java.text.ParseException
     */
    @RequestMapping(value = "create/{isbn}/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object emprunter(@PathVariable("isbn") String isbn, @PathVariable("login") String login) throws ParseException, MessagingException {
        log.debug("REST request to emprunter  Livre : {}", isbn);
        Optional<Livre> lv = livreRepository.findOneByIsbn(isbn);
        User u = userRepository.findByLogin(login);
        Map<String, Object> modele = new HashMap<>();

        if (!lv.isPresent()) {
            modele.put(Constants.MESSAGE, "Le livre avec cet IDBN " + isbn + " n'existe pas");
            modele.put(Constants.ERROR, true);
            return modele;
        }
        if (u == null) {
            modele.put(Constants.MESSAGE, "Le membre avec Ce login  <<" + login + ">> n'existe pas");
            modele.put(Constants.ERROR, true);
            return modele;
        }
        Livre livre = lv.get();

        Emprunt e = new Emprunt(u, livre, null);

        empruntRepository.save(e);
        modele.put(Constants.MESSAGE, "Opération réussie \n Reference : " + e.getReference() + "\n Titre : " + e.getLivre().getTitre());

        modele.put("reference", e.getReference());

        modele.put("titre", e.getLivre().getTitre());
        String s = "M ou Mm "+u.getNom()+" "+u.getPrenom()+ " tel "+u.getTel()
                + " vient d'emprunter le livre : \n  Titre :"
                + livre.getTitre() + "\n ISBN " + livre.getIsbn() + " \n Auteur :" + livre.getAuteurs()
                + "\n Date d'emprunt: " + Utils.formatDate(e.getDateEmprunt());

        
        System.out.println("GGGGGGGGGGG  "+s);
        log.debug("REST request to emprunter  Livre : {}", s);
        List<User> lu = userRepository.findByProfile(Profile.ADMIN);
        
        System.out.println("");
        for (User us : lu) {
            if (us.getEmail() != null) {

                mailService.sendEmail(us.getEmail(), "Emprunt de livre", s, true, true);

            }
        }

        return modele;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     *
     * @param parameters parametres de recherche : date emprunt min et max , mot
     * clé
     * @return liste des empruns
     * @throws ParseException
     */
    @RequestMapping(value = "searche", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object searcheByParameters(@RequestBody SearcheParameters parameters) throws ParseException {
        if (parameters.getDateDebut() == null) {
            parameters.setDateDebut(sdf.parse("2017-03-01 00:00:00.000"));
        }
        if (parameters.getDateFin() == null) {
            parameters.setDateFin(new Date());
        }
        if (parameters.getMoCle() == null) {
            parameters.setMoCle("");
        }

        if (parameters.getPage() == null) {
            parameters.setPage(0);
        }
        if (parameters.getSize() == null) {
            parameters.setPage(20);
        }
        System.out.println("1");
        parameters.setMoCle("%" + parameters.getMoCle() + "%");
        List<Emprunt> le = empruntRepository.findByDateMoTCleAndDateEmprunt(parameters.getMoCle(), parameters.getDateDebut(), parameters.getDateFin());

        List<Object> lo = new ArrayList<>();

        for (Emprunt l : le) {
            Map<String, Object> modele = new HashMap<>();
            modele.put("nom", l.getMembre().getNom() + " " + l.getMembre().getPrenom());
            modele.put("tel", l.getMembre().getTel());
            modele.put("email", l.getMembre().getId());
            modele.put("id", l.getId());
            modele.put("isbn", l.getLivre().getIsbn());
            modele.put("titre", l.getLivre().getTitre());
            modele.put("auteurs", l.getLivre().getAuteurs());
            modele.put("edition", l.getLivre().getEdition());
            modele.put("reference", l.getReference());
            modele.put("date", sdf.format(l.getDateEmprunt()));

            lo.add(modele);
        }

        return lo;
    }

    /**
     *
     * @param parameters parametres de recherche : date emprunt min et max , mot
     * clé, page et size
     * @return Page des empruns
     * @throws ParseException
     */
    @RequestMapping(value = "searchepage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

    public Object searcheByParametersps(@RequestBody SearcheParameters parameters) throws ParseException {
        if (parameters.getDateDebut() == null) {
            parameters.setDateDebut(sdf.parse("2017-03-01 00:00:00.000"));
        }
        if (parameters.getDateFin() == null) {
            parameters.setDateFin(new Date());
        }
        if (parameters.getMoCle() == null) {
            parameters.setMoCle("");
        }

        if (parameters.getPage() == null) {
            parameters.setPage(0);
        }
        if (parameters.getSize() == null) {
            parameters.setPage(20);
        }
        System.out.println("1");
        parameters.setMoCle("%" + parameters.getMoCle() + "%");

        System.out.println("kkk " + parameters.getMoCle());
        Page<Emprunt> le = empruntRepository.findByDateMoTCleAndDateEmprunt(parameters.getMoCle(), parameters.getDateDebut(), parameters.getDateFin(), new PageRequest(parameters.getPage(), parameters.getSize()));

        List<Object> lo = new ArrayList<>();

        for (Emprunt l : le) {
            Map<String, Object> modele = new HashMap<>();
            modele.put("nom", l.getMembre().getNom() + " " + l.getMembre().getPrenom());
            modele.put("tel", l.getMembre().getTel());
            modele.put("email", l.getMembre().getEmail());
            modele.put("id", l.getId());
            modele.put("isbn", l.getLivre().getIsbn());
            modele.put("titre", l.getLivre().getTitre());
            modele.put("auteurs", l.getLivre().getAuteurs());
            modele.put("edition", l.getLivre().getEdition());
            modele.put("reference", l.getReference());
            modele.put("date", sdf.format(l.getDateEmprunt()));

            lo.add(modele);
        }
        Map<String, Object> modele2 = new HashMap<>();
        modele2.put("content", lo);
        modele2.put("size", le.getSize());
        modele2.put("totalElements", le.getTotalElements());
        modele2.put("numberOfElements", le.getNumberOfElements());
        modele2.put("number", le.getNumber());
        modele2.put("totalPages", le.getTotalPages());
        modele2.put("dateDebut", sdf.format(parameters.getDateDebut()));
        modele2.put("dateFin", sdf.format(parameters.getDateFin()));
        return modele2;
    }
}
