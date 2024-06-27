package com.guidebook.GuideBook.Controller.admin;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Services.admin.AdminLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/admin")
public class AdminLanguageController {

    private AdminLanguageService adminLanguageService;
    @Autowired
    AdminLanguageController(AdminLanguageService adminLanguageService){
        this.adminLanguageService = adminLanguageService;
    }

    @GetMapping("/languages")
    public List<Language> getAllLanguages() {
        return adminLanguageService.getAllLanguages();
    }

    @GetMapping("/languages/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Long id) {
        Language language = adminLanguageService.getLanguageById(id);
        if (language != null) {
            return ResponseEntity.ok(language);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addLanguage")
    public ResponseEntity<Language> addLanguage(@RequestBody Language language) {
        Language savedLanguage = adminLanguageService.addLanguage(language);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLanguage);
    }

    @PutMapping("/updateLanguage/{id}")
    public ResponseEntity<Language> updateLanguageById(@PathVariable Long id, @RequestBody Language language) {
        language.setLanguageId(id);
        Language updatedLanguage = adminLanguageService.updateLanguageById(id, language);
        if(updatedLanguage!=null){
            return ResponseEntity.ok(updatedLanguage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("deleteLanguage/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        adminLanguageService.deleteLanguageById(id);
        return ResponseEntity.noContent().build();
    }
}
