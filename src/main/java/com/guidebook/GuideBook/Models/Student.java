
package com.guidebook.GuideBook.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guidebook.GuideBook.enums.StudentClassType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//Below is a lombok annotation for making private all the non static properties
//@FieldDefaults(level = AccessLevel.PRIVATE) - Use it later. Now just go with writing things
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private Long studentMis;
    private String studentName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_collegeId_studentId")
    private College college; //Done

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_branchId_studentId")
    private Branch studentBranch; //Done


    private double cetPercentile; //set contraints on this later if possible
    private double grade;//set contraints on this later if possible
    @Enumerated(value = EnumType.STRING)
    private StudentClassType studentClassType; //Done

    @ManyToMany
    @JoinTable(
            name = "student_language",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "languageId")
    )
    private List<Language> studentLanguageList; //Remaining

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "studentProfileId")
    @JsonIgnore //ignores this when fetching list of students to the frontend
    private StudentProfile studentProfile; //Remaining

//    public StudentProfile getStudentProfile() {
//        return studentProfile; //Remaining
//    }
}

