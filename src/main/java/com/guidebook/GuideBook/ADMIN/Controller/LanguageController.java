package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.Models.Language;
import com.guidebook.GuideBook.ADMIN.Services.LanguageService;
import com.guidebook.GuideBook.ADMIN.dtos.AddLanguageRequest;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllLanguageNameListResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.AlreadyPresentException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com", "https://a.guidebookx.com",
        "https://13.235.131.222",
        "https://diugkigakpnwm.cloudfront.net"})
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
            ) throws AlreadyPresentException {
        Language res = languageService.addLanguage(addLanguageRequest.getLanguage());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
