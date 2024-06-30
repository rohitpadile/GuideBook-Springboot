
package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentProfileId;

    private String studentYear;

    @Column(length = 1000) // Example length for resume
    private String resume;

    @Column(length = 2000) // Example length for work history
    private String workHistory;//skills


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private Student student;

//    public Student getStudent() {
//        return student;
//    }

    //Add more File, images references link in more columns later
}

