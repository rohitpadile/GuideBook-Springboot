package com.guidebook.GuideBook.Controller.admin;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Services.admin.AdminLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class AdminLanguageController {

    private AdminLanguageService adminLanguageService;
    @Autowired
    AdminLanguageController(AdminLanguageService adminLanguageService){
        this.adminLanguageService = adminLanguageService;
    }

    @GetMapping
    public List<Language> getAllLanguages() {
        return adminLanguageService.getAllLanguages();
    }

    @PostMapping
    public ResponseEntity<Language> createLanguage(@RequestBody Language language) {
        Language savedLanguage = adminLanguageService.saveOrUpdateLanguage(language);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLanguage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        adminLanguageService.deleteLanguageById(id);
        return ResponseEntity.noContent().build();
    }
}
