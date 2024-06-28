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
    private double cetPercentile; //set contraints on this later if possible
    private double grade;//set contraints on this later if possible
    private String yearOfStudy;

    @OneToMany
    @JoinColumn(name = "languageId")
    private List<Language> studentLanguageList;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    @JoinColumn(name = "studentProfileId")
    private StudentProfile studentProfile;

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }
}


//SY, TY, DSY like info will be in the StudentProfile as they are not significant like branch and college.
//It is handled by student himself or by us