package com.guidebook.GuideBook.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    private String branchName;

    @JsonIgnore
    @ManyToMany(mappedBy = "collegeBranchList")
    private Set<College> branchCollegeList = new HashSet<>();

    @OneToMany//owner side
    @JoinColumn(name = "fk_branchId_studentId", referencedColumnName = "branchId")
    private List<Student> branchStudentList;
}

