package com.guidebook.GuideBook.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Valid
public class AddCollegeClubPostRequest {

    @NotEmpty
    @NotNull
    private String collegeClubName;

    @NotEmpty
    @NotNull
    private String collegeClubPostDescription;

    private List<String> collegeClubPostMediaPaths;

    // Constructor, getters, and setters can be generated using Lombok

    // If you are using Lombok, ensure you have the plugin configured in your IDE or build tool
}