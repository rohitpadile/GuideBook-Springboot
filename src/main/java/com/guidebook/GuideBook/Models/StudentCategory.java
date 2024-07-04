package com.guidebook.GuideBook.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data //getters and setters
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long studentCategoryId;
    String studentCategoryName;
    @JsonIgnore
    @OneToMany(mappedBy = "studentCategory")
    List<Student> studentCategoryStudentList;
}
