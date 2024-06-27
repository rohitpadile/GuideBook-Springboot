package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String studentName;
    @ManyToOne
    @JoinColumn(name = "collegeId")
    private College college;

    @ManyToOne
    @JoinColumn(name = "branchId")
    private Branch branch;

//    @Column(precision = 10, scale = 8) // Assuming cetPercentile can have up to 7-8 decimal digits
    private double cetPercentile;

//    @Column(precision = 4, scale = 2) // Assuming grade (CGPA) is out of 10 and can have up to 2 decimal digits
    private double grade;

//    @ManyToMany
//    @JoinTable(
//            name = "language",
//            joinColumns = @JoinColumn(name = "studentId"),
//            inverseJoinColumns = @JoinColumn(name = "languageId")
//    )
//    private List<Language> languages = new ArrayList<>();

    @ElementCollection
    private List<Long> studentLanguageIds = new ArrayList<>();
    private Integer yearOfStudy;//2024
}


//SY, TY, DSY like info will be in the StudentProfile as they are not significant like branch and college.
//It is handled by student himself or by us