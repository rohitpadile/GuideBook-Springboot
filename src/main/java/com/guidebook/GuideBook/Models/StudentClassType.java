package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentClassType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long studentClassTypeId;

    String studentClassTypeName;

    @OneToMany(mappedBy = "studentClassType")
    @Column(unique = true)
    List<Student> studentClassTypeStudentList;
}
