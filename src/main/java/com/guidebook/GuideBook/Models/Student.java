
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
@Data //getters and setters
@Builder
//Below is a lombok annotation for making private all the non static properties
//@FieldDefaults(level = AccessLevel.PRIVATE) - Use it later. Now just go with writing things
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private Long studentMis;
    private String studentName;

    @ManyToOne
    @JoinColumn(name = "fk_studentId_collegeId", referencedColumnName = "collegeId")
    private College studentCollege; //owning side

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_studentId_branchId", referencedColumnName = "branchId")
    private Branch studentBranch; //owning side

    private Double cetPercentile; //set contraints on this later if possible
    private Double grade;//set contraints on this later if possible
    @Enumerated(value = EnumType.STRING)
    private StudentClassType studentClassType; //Done

    @ManyToMany //owning side
    @JoinTable(
            name = "student_language",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "languageId")
    )
    private List<Language> studentLanguageList;

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY) //owning side
    @JoinColumn(name = "studentProfileId")
    @JsonIgnore //ignores this when fetching list of students to the frontend //otherwise create a DTO directy.
    private StudentProfile studentProfile;

}

