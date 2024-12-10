package com.ameda.dev.works.barcode.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;

@Service
public class DocumentGeneratorService {

    private final TemplateEngine templateEngine;

    public DocumentGeneratorService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    private final TransformerFactory transformerFactory = TransformerFactory.newInstance();

    public void generateDocument(HttpServletRequest request, HttpServletResponse response){
        String data = RandomStringUtils.random(20,0,0,true,true,null).toUpperCase();
        Context context = new Context(Locale.getDefault(), Map.of("model",Map.of(
                "title", "BarCode Generation Using Apache FOP",
                "titleLine1", "and Barcode4J",
                "code39Message",data)));
        String xmlTemplate = templateEngine.process("code39",context);
        try {
            response.setContentType("application/pdf");
            FopFactory fopFactory = FopFactory.newInstance();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, response.getOutputStream());
            Transformer transformer = transformerFactory.newTransformer();
            Source src = new StreamSource(new StringReader(xmlTemplate));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } catch (Exception ex) {

            System.out.println("An error occurred: {}"+ex.getMessage());
        }
    }
    }

