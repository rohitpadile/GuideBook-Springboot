package com.guidebook.GuideBook.TR.Controller;

import com.guidebook.GuideBook.ADMIN.Controller.LanguageController;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllLanguageNameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/teamRecruiter/")
public class TRLanguageController {
    private LanguageController languageController;
    @Autowired
    public TRLanguageController(LanguageController languageController) {
        this.languageController = languageController;
    }

    @GetMapping("/getAllLanguages")
    public ResponseEntity<GetAllLanguageNameListResponse> getAllLanguageNamesList(){
        return languageController.getAllLanguageNamesList();
    }
}
