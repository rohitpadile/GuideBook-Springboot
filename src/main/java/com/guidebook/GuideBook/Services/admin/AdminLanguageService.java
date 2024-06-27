package com.guidebook.GuideBook.Services.admin;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminLanguageService {

    private LanguageRepository languageRepository;

    @Autowired
    public AdminLanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Language getLanguageById(Long id) {
        Optional<Language> language = languageRepository.findById(id);
        return language.orElse(null);
    }

    public Language addLanguage(Language language) {
        return languageRepository.save(language);
    }

    public Language updateLanguageById(Long id, Language updatedLanguage) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (optionalLanguage.isPresent()) {
            Language language = optionalLanguage.get();
            language.setLanguageName(updatedLanguage.getLanguageName());
            // Set other fields to update as needed

            return languageRepository.save(language);
        } else {
            return null; // or throw exception, depending on your use case
        }
    }

    public void deleteLanguageById(Long id) {
        languageRepository.deleteById(id);
    }
}

