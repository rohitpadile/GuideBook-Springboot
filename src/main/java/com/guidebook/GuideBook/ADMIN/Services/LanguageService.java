package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Models.Language;
import com.guidebook.GuideBook.ADMIN.Repository.LanguageRepository;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllLanguageNameListResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.AlreadyPresentException;
import com.guidebook.GuideBook.ADMIN.exceptions.LanguageNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    private LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }
    @Transactional
    public Language addLanguage(String languageName)
            throws AlreadyPresentException {
        if((languageRepository.findLanguageByLanguageNameIgnoreCase(languageName)) != null){
            throw new AlreadyPresentException("Language already present at addLanguage() method: " + languageName);
        }
        Language newLanguage = new Language();
        newLanguage.setLanguageName(languageName);
        return languageRepository.save(newLanguage);
    }

    public Language GetLanguageByLanguageNameIgnoreCase(String name) throws LanguageNotFoundException {
        return languageRepository.findLanguageByLanguageNameIgnoreCase(name);
    }

    public GetAllLanguageNameListResponse getAllLanguageNamesList() {
        GetAllLanguageNameListResponse response = new GetAllLanguageNameListResponse();
        List<Language> languages = languageRepository.findAll();

        for(Language language : languages){
            response.getAllLanguageNamesList().add(language.getLanguageName());
        }

        return response;
    }


}

