package com.guidebook.GuideBook.Services.admin;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Repository.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    private LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Language getLanguageById(Long id) {
        return languageRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Language not found with id: " + id)
        );
    }

    public Language addLanguage(Language language) {
        return languageRepository.save(language);
    }

    public Language updateLanguageById(Long id, Language language) {
        Language exisitingLanguage = languageRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Language not found with id: " + id)
        );
        if(language.getLanguageName()!=null){
            exisitingLanguage.setLanguageName(language.getLanguageName());
        }
        return languageRepository.save(exisitingLanguage);
    }

    public void deleteLanguageById(Long id) {
        languageRepository.deleteById(id);
    }
}

