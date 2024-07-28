package com.guidebook.GuideBook.dtos;

import com.guidebook.GuideBook.embeddables.studentProfile.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class GetStudentProfileResponse {
    //LATER CHANGE LIST<STRING> TO ORIGINAL CUSTOM DATATYPES OBJECTS - DONE
//Work email neither goes in nor goes out - only be accessed by admin from the servers
    private String studentPublicEmail;
    private List<AboutSection> studentProfileAboutSection;
    private List<CityOfCoaching> studentProfileCityOfCoaching;
    private List<ExamScoreDetails> studentProfileExamScoreDetails;
    private List<OtherExamScoreDetails> studentProfileOtherExamScoreDetails;
    private List<ActivityAndAchievements> studentProfileActivityAndAchievements;
//    private List<CoCurricularActivity> studentProfileCoCurricularActivity;//removed
//    private List<ExtraCurricularActivity> studentProfileExtraCurricularActivity;//removed
    private List<TutoringExperience> studentProfileTutoringExperience;
    private List<ExternalLink> studentProfileExternalLinks;
    private Long studentProfileSessionsConducted;
}