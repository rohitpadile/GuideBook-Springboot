package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Models.StudentClassType;
import com.guidebook.GuideBook.Repository.LanguageRepository;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllLanguageNameListResponse;
import com.guidebook.GuideBook.exceptions.LanguageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageService {

    private LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Language addLanguage(String languageName) {
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

