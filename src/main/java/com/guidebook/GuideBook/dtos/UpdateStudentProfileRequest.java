package com.guidebook.GuideBook.dtos;

import com.guidebook.GuideBook.embeddables.*;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;
@Data
@Valid
public class UpdateStudentProfileRequest {
//MIS IS IN PATH VARIABLE OKAY!
    private List<AboutSection> AboutSection;
    private List<CityOfCoaching> CityOfCoaching;
    private List<ExamScoreDetails> ExamScoreDetails;
    private List<OtherExamScoreDetails> OtherExamScoreDetails;
    private List<AcademicActivity> AcademicActivity;
    private List<CoCurricularActivity> CoCurricularActivity;
    private List<ExtraCurricularActivity> ExtraCurricularActivity;
    private List<TutoringExperience> TutoringExperience;
    private List<ExternalLink> ExternalLinks;
}
