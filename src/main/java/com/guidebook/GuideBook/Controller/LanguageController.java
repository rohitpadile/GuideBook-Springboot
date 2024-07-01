package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Services.LanguageService;
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
    public ResponseEntity<List<Language>> getAllLanguages() {
        List<Language> languages = languageService.getAllLanguages();
        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

    @GetMapping("/languages/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Long id) {
        Language language = languageService.getLanguageById(id);
        return new ResponseEntity<>(language, HttpStatus.OK);
    }

    @PostMapping("/addLanguage")
    public ResponseEntity<Language> addLanguage(@RequestBody Language language) {
        Language savedLanguage = languageService.addLanguage(language);
        return new ResponseEntity<>(savedLanguage, HttpStatus.CREATED);
    }

    @PutMapping("/updateLanguage/{id}")
    public ResponseEntity<Language> updateLanguageById(@PathVariable Long id, @RequestBody Language language) {
        Language updatedLanguage = languageService.updateLanguageById(id, language);
        return new ResponseEntity<>(updatedLanguage, HttpStatus.OK);
    }

    @DeleteMapping("deleteLanguage/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        languageService.deleteLanguageById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
