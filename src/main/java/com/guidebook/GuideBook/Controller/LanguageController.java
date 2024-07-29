package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Services.LanguageService;
import com.guidebook.GuideBook.dtos.AddLanguageRequest;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllLanguageNameListResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080",
        "https://dwyh4gudx0a9x.cloudfront.net",
        "https://guidebookx.com"})
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

    @PostMapping("/addLanguage")
    public ResponseEntity<Language> addLanguage(
            @RequestBody @Valid AddLanguageRequest addLanguageRequest
            ){
        Language res = languageService.addLanguage(addLanguageRequest.getLanguage());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
