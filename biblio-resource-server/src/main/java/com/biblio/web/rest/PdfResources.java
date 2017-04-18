/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.web.rest;

import com.biblio.entity.Livre;
import com.biblio.repository.LivreRepository;
import com.biblio.web.rest.util.GenerateQRCode;
import com.google.zxing.WriterException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/pdf")
public class PdfResources {

    @Inject
    private LivreRepository livreRepository;

    @RequestMapping(value = "livre", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, WriterException {
        response.setContentType("application/pdf");
        GenerateQRCode ge = new GenerateQRCode();

        try {

            Document document = new Document();
            String param = request.getParameter("isbn");
            Livre livre = livreRepository.findOneByIsbn(param).get();
            PdfWriter e = PdfWriter.getInstance(document, response.getOutputStream());

            document.open();


             Font font = new Font();
             
             font.setStyle(Font.BOLD);
             font.setSize(12);
              
            List list = new List(15);


            
            //  document.left(12);
            list.add(new ListItem("Titre  :" + livre.getTitre(), font));
            list.add(new ListItem("Categorie  :" + livre.getCategorie().getDescription(), font));
            list.add(new ListItem("Auteurs   :" + livre.getAuteurs(), font));
            list.add(new ListItem("Edition   :" + livre.getEdition(), font));
            list.add(new ListItem("Editeur   :" + livre.getEditeur(), font));
            list.add(new ListItem("Collection   :" + livre.getCollection(), font));
            list.add(new ListItem("Date parution   :" + livre.getDateParution(), font));
            list.add(new ListItem("Isbn   " + livre.getIsbn(), font));
            list.add(new ListItem("Resume   : " + livre.getResume(), font));

            document.add(list);
            document.addTitle(livre.getTitre());
            document.setMargins(100, 20, 0, 0);
            document.addCreationDate();
            System.out.println("TTT v "+  document.addTitle(param));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(ge.createQRImage(param, 125), "jpg", baos);
            Image image = Image.getInstance(baos.toByteArray());
            document.add(image);
            document.close();

        } catch (DocumentException de) {
            throw new IOException(de.getMessage());
        }
    }
}
