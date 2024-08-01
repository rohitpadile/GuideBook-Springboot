
package com.guidebook.GuideBook.ADMIN.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MAKE THIS UUID LATER
    private Long studentId;
    @Column(unique = true)
    private String studentWorkEmail; //Search details about the student through email only
    private String studentPublicEmail;
    private Long studentMis;
    private String studentName; //Rohit Padile - There can be multiple names
    private Double cetPercentile;
    private Double grade;
    @ManyToOne
    @JoinColumn(name = "fk_studentId_studentCategoryId", referencedColumnName = "studentCategoryId")
    private StudentCategory studentCategory;//owning side
    //THIS IS NECESSARY TO TARGET AND TALK SPECIFIC STUDENTS.
    //OBC STUDENTS CAN TALK TO OBC STUDENTS, THIS FEATURE AND CAN VISIBLE-ENABLE OR DISABLE - KEEP AN OPTION
    //IN THE JAVASCRIPT
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
    @ManyToMany
    @JoinTable(
            name = "student_language",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "languageId")
    )
    private List<Language> studentLanguageList;//owning side


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date updatedOn;
    @Version
    @JsonIgnore
    private Integer version;


    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<ZoomSessionTransaction> zoomSessionTransactions;
}

