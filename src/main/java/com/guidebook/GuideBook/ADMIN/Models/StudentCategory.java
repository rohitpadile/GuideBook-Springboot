package com.guidebook.GuideBook.ADMIN.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
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
