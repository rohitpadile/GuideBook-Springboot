package com.guidebook.GuideBook.ADMIN.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE)
public class EntranceExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long entranceExamId;
    String entranceExamName;
    @ManyToMany(mappedBy = "collegeEntranceSet")
    @JsonIgnore
    private Set<College> entranceExamCollegeSet;

}
