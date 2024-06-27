package com.guidebook.GuideBook.Services.admin;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminLanguageService {

    private LanguageRepository languageRepository;
    @Autowired
    AdminLanguageService(LanguageRepository languageRepository){ //constructor field injection preferred.
        this.languageRepository =  languageRepository;
    }
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Language saveOrUpdateLanguage(Language language) {
        return languageRepository.save(language);
    }

    public void deleteLanguageById(Long id) {
        languageRepository.deleteById(id);
    }



}
