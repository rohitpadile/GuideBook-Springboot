package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String collegeClubCollegeName;

    private Long collegeClubFollowers;

    @ManyToOne
    @JoinColumn(name = "fk_collegeClubId_collegeId", referencedColumnName = "collegeId")
    private College collegeClubCollege; //owning side

}
