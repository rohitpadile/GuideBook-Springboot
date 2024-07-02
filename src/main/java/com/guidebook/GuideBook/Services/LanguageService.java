package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

    private LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Language addLanguage(Language language) {
        return languageRepository.save(language);
    }

    public Language GetLanguageByLanguageNameIgnoreCase(String name){
        return languageRepository.findLanguageByLanguageNameIgnoreCase(name);
    }

}

