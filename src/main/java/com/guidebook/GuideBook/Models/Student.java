
package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data //getters and setters
@Builder
//Below is a lombok annotation for making private all the non static properties
//@FieldDefaults(level = AccessLevel.PRIVATE) - Use it later. Now just go with writing things
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private Long studentMis; //Search details about the student through MIS only
    private String studentName; //Rohit Padile - There can be multiple names
    private Double cetPercentile;
    private Double grade;
    //ABOVE PROPERTIES ARE USED IN StudentMapper
    @ManyToOne
    @JoinColumn(name = "fk_studentId_collegeId", referencedColumnName = "collegeId")
    private College studentCollege; //owning side
    @ManyToOne
    @JoinColumn(name = "fk_studentId_branchId", referencedColumnName = "branchId")
    private Branch studentBranch; //owning side
    @ManyToOne //owning side
    @JoinColumn(name = "fk_studentId_studentClassTypeId", referencedColumnName = "studentClassTypeId")
    private StudentClassType studentClassType; //EX: FY_BTECH, SY_MTECH
    @ManyToMany //owning side
    @JoinTable(
            name = "student_language",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "languageId")
    )
    private List<Language> studentLanguageList;

}

