package com.guidebook.GuideBook.dtos;

import com.guidebook.GuideBook.enums.StudentClassType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;

@Builder
@Data
public class FilteredStudentListRequest {//edit this dto after reading the minor project code.
    @NotEmpty(message = "Branch name cannot be empty")
    private String branchName;

    @Min(value = 0, message = "Minimum grade should not be less than 0")
    private Double minGrade;

    @Min(value = 0, message = "Minimum CET percentile should not be less than 0")
    private Double minCetPercentile;

    @NotNull(message = "Student class type cannot be null")
    private StudentClassType studentClassType;

    @NotEmpty(message = "Language name cannot be empty")
    private String languageName;
}
