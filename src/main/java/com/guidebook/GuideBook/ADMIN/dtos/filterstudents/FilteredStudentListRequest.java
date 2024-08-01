package com.guidebook.GuideBook.ADMIN.dtos.filterstudents;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class FilteredStudentListRequest {//edit this dto after reading the minor project code.

    @NotNull(message = "College name is strictly not null")
    private String collegeName;
    @NotNull(message = "Branch name cannot be empty")
    private String branchName;

    @Min(value = 0, message = "Minimum grade should not be less than 0")
    private Double minGrade;

    @Min(value = 0, message = "Minimum CET percentile should not be less than 0")
    private Double minCetPercentile;

    @NotNull(message = "Student class type cannot be null")
    private String studentClassType;

    @NotNull(message = "Language name cannot be empty")
    private String languageName;

    @NotNull(message = "Student category cannot be null")
    private String studentCategory;
}
