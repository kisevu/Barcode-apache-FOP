package com.ameda.dev.works.barcode.api;


import com.ameda.dev.works.barcode.service.DocumentGeneratorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {
    private final DocumentGeneratorService documentGeneratorService;

    public DocumentController(DocumentGeneratorService documentGeneratorService) {
        this.documentGeneratorService = documentGeneratorService;
    }

    @GetMapping("/document")
    public void getDocument(HttpServletRequest request, HttpServletResponse response){
        documentGeneratorService.generateDocument(request,response);
    }
}
