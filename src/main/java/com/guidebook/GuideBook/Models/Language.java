package com.guidebook.GuideBook.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long languageId;

    private String languageName;
}
