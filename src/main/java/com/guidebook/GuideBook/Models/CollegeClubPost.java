package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CollegeClubPost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String collegeClubPostId;

    private String collegeClubPostDescription;
    @ElementCollection
    @CollectionTable(name = "collegePost_MediaPaths", joinColumns = @JoinColumn(name = "collegePostId"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL) // Cascade delete
    private List<String> collegeClubPostMediaPaths;
}
