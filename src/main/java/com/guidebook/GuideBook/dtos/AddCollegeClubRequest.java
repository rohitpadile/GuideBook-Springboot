package com.guidebook.GuideBook.dtos;

import jakarta.validation.Valid;
import lombok.Data;

@Valid
@Data
public class AddCollegeClubRequest {
    private String collegeClubName;
    private String collegeClubDescription;
    private String collegeClubBannerPath;
    private String collegeClubCollegeName;

}
