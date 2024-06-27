package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    //SY, TY, DSY like info will be in the StudentProfile as they are not significant like branch and college.
    //It is handled by student himself or by us
}
