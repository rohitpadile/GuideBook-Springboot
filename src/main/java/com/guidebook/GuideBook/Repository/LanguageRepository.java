package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Language findLanguageByLanguageName(String name);
}
