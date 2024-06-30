
package com.guidebook.GuideBook.Models;

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
    private Set<Branch> collegeBranchList = new HashSet<>();

    @OneToMany //owning side
    @JoinColumn(name = "fk_collegeId_studentId", referencedColumnName = "collegeId")
    private List<Student> collegeStudentList;

}

