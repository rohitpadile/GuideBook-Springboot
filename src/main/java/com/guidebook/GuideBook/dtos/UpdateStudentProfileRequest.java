package com.guidebook.GuideBook.dtos;

import com.guidebook.GuideBook.embeddables.*;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;
@Data
@Valid
public class UpdateStudentProfileRequest {
//MIS IS IN PATH VARIABLE OKAY!
//work email neither goes in nor goes out - only be accessed by owner from servers
    private String studentPublicEmail;
    private List<AboutSection> studentProfileAboutSection;
    private List<CityOfCoaching> studentProfileCityOfCoaching;
    private List<ExamScoreDetails> studentProfileExamScoreDetails;
    private List<OtherExamScoreDetails> studentProfileOtherExamScoreDetails;
    private List<AcademicActivity> studentProfileAcademicActivity;
    private List<CoCurricularActivity> studentProfileCoCurricularActivity;
    private List<ExtraCurricularActivity> studentProfileExtraCurricularActivity;
    private List<TutoringExperience> studentProfileTutoringExperience;
    private List<ExternalLink> studentProfileExternalLinks;
}
