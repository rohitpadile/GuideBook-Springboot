package com.guidebook.GuideBook.dtos;

import com.guidebook.GuideBook.embeddables.ExternalLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentProfileResponse {
    private Long studentMis;
    private List<String> studentProfileAboutSection;
    private List<String> studentProfileCityOfCoaching;
    private List<String> studentProfileExamScoreDetails;
    private List<String> studentProfileOtherExamScoreDetails;
    private List<String> studentProfileAcademicActivity;
    private List<String> studentProfileCoCurricularActivity;
    private List<String> studentProfileExtraCurricularActivity;
    private List<String> studentProfileTutoringExperience;
    private List<ExternalLink> studentProfileExternalLink;
    private Long studentProfileSessionsConducted;
}