
package com.guidebook.GuideBook.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Branch> collegeBranchSet = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "studentCollege")
    private List<Student> collegeStudentList;

    //Add Entrance Exam property here to filter colleges based on selected Entrance-Exam.
    //THIS CAN BE ADDED IN FUTURE ALSO
}

