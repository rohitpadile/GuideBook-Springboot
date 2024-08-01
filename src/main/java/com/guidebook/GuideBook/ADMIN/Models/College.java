
package com.guidebook.GuideBook.ADMIN.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collegeId;
    private String collegeName;

    @ManyToMany //owning side
    @JoinTable(name = "college_branches",
        joinColumns = @JoinColumn(name = "collegeId"),
        inverseJoinColumns = @JoinColumn(name = "branchId")
    )
    private Set<Branch> collegeBranchSet;

    @JsonIgnore
    @OneToMany(mappedBy = "studentCollege")
    private List<Student> collegeStudentList;

    @ManyToMany
    @JoinTable(name = "college_entrance",
        joinColumns = @JoinColumn(name = "collegeId"),
            inverseJoinColumns = @JoinColumn(name = "entranceExamId")
    )
    private Set<EntranceExam> collegeEntranceSet;

//    @JsonIgnore
//    @OneToMany
//    private Set<CollegeClub> collegeClubSet;

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
}

