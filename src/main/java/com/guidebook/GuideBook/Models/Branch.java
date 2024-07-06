
package com.guidebook.GuideBook.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
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


    @ManyToMany(mappedBy = "collegeBranchSet")
    @JsonIgnore
    private Set<College> branchCollegeSet;


    @OneToMany(mappedBy = "studentBranch")
    @JsonIgnore
    private List<Student> branchStudentList;

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


