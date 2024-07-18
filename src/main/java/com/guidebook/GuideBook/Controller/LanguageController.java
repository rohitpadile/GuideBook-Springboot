package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Services.LanguageService;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllLanguageNameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = {"http://guidebookx.s3-website.ap-south-1.amazonaws.com", "http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin")
public class LanguageController {

    private LanguageService languageService;
    @Autowired
    LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/languages")
    public ResponseEntity<GetAllLanguageNameListResponse> getAllLanguageNamesList(){
        GetAllLanguageNameListResponse res = languageService.getAllLanguageNamesList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
