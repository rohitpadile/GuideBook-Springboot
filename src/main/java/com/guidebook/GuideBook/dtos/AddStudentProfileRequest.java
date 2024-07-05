package com.guidebook.GuideBook.dtos;

import com.guidebook.GuideBook.embeddables.*;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Valid
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddStudentProfileRequest {
    @NotNull
    private String studentWorkEmail;
    @NotNull
    private List<AboutSection> studentProfileAboutSection;
    private List<CityOfCoaching> studentProfileCityOfCoaching;
    private List<ExamScoreDetails> studentProfileExamScoreDetails;
    private List<OtherExamScoreDetails> studentProfileOtherExamScoreDetails;
    private List<AcademicActivity> studentProfileAcademicActivity;
    private List<CoCurricularActivity> studentProfileCoCurricularActivity;
    private List<ExtraCurricularActivity> studentProfileExtraCurricularActivity;
    private List<TutoringExperience> studentProfileTutoringExperience;

    private List<ExternalLink> studentProfileExternalLink;

}
