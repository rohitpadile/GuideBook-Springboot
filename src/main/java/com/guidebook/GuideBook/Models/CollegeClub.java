package com.guidebook.GuideBook.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CollegeClub {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String collegeClubId;

    private String collegeClubName;

    //OTHER PROPERTIES TO SHOW ON ITS PROFILE

    private String collegeClubDescription;

    private String collegeClubBannerPath;

    private Long collegeClubFollowers;

    @ManyToOne
    @JoinColumn(name = "fk_collegeClubId_collegeId", referencedColumnName = "collegeId")
    private College collegeClubCollege; //owning side

    @JsonIgnore
    @OneToMany(mappedBy = "collegeClubPostClub")
    private List<CollegeClubPost> collegeClubPosts;

}
