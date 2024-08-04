package com.guidebook.GuideBook.ADMIN.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Valid
public class AddStudentRequest {
    @NotNull(message = "Student name cannot be null")
    private String studentName;
    @NotNull(message = "Mis is unique identifier for student which cannot be null")
    private Long studentMis;
    @NotNull(message = "This is very important - not null")
    private String studentWorkEmail;
    private String studentPublicEmail;
    @NotNull(message = "Student college is compulsory field")
    private String studentCollegeName;
    @NotNull(message = "Student branch is compulsory field")
    private String studentBranchName;
    @NotNull(message = "Student cet percentile is required")
    private Double studentCetPercentile;
    @NotNull(message = "Student current CGPA is also required") //Think about this field after discussing with people
    private Double studentGrade;//how this filter would look like - IS THIS REALLY NECESSARY FIELD. //I THINK IT IS
    @NotNull(message = "Student classType is compulsory field")
    private String studentClassType;

    private String studentCategoryName;
    @NotNull(message = "Student should speak at least one language, so this can't be null")
    private List<String> studentLanguageNames;

    //    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY) //owning side
    //    @JoinColumn(name = "studentProfileId")
    //    private StudentProfile studentProfile;

    //FOR PROFILE - SEPARATE DTO WILL DO.

}
