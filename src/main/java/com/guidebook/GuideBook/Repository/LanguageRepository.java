package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
