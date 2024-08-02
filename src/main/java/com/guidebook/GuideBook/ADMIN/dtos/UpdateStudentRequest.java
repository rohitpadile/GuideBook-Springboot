package com.guidebook.GuideBook.ADMIN.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdateStudentRequest {
    private String studentName;
    private Long studentMis;
    @NotNull(message = "Student work email cannot be null to update the student details")
    private String studentWorkEmail;
    private String studentPublicEmail;
    private String studentCollegeName;
    private String studentBranchName;
    private Double studentCetPercentile;
    private Double studentGrade;
    private String studentClassType;
    private String studentCategoryName;
    private List<String> studentLanguageNames;
}
