package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentProfileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private Student student;

    // Additional profile attributes (resume, work history, skills, etc.)
    @Column(length = 1000) // Example length for resume
    private String resume;

    @Column(length = 2000) // Example length for work history
    private String workHistory;

    @ElementCollection // Use ElementCollection for a collection of basic types (e.g., skills)
    private List<String> skills;

    //Add more File, images references link in more columns later
}
