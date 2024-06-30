package com.guidebook.GuideBook.Controller.admin;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Services.admin.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/admin")
public class LanguageController {

    private LanguageService languageService;
    @Autowired
    LanguageController(LanguageService languageService){
        this.languageService = languageService;
    }

    @GetMapping("/languages")
    public List<Language> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    @GetMapping("/languages/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Long id) {
        Language language = languageService.getLanguageById(id);
        if (language != null) {
            return ResponseEntity.ok(language);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addLanguage")
    public ResponseEntity<Language> addLanguage(@RequestBody Language language) {
        Language savedLanguage = languageService.addLanguage(language);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLanguage);
    }

    @PutMapping("/updateLanguage/{id}")
    public ResponseEntity<Language> updateLanguageById(@PathVariable Long id, @RequestBody Language language) {
        language.setLanguageId(id);
        Language updatedLanguage = languageService.updateLanguageById(id, language);
        if(updatedLanguage!=null){
            return ResponseEntity.ok(updatedLanguage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("deleteLanguage/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        languageService.deleteLanguageById(id);
        return ResponseEntity.noContent().build();
    }
}
