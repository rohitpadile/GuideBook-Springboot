package com.guidebook.GuideBook.dtos;

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
    private Long studentMis;
    @NotNull
    private List<String> studentProfileAboutSection;
    private List<String> studentProfileCityOfCoaching;
    private List<String> studentProfileExamScoreDetails;
    private List<String> studentProfileOtherExamScoreDetails;
    private List<String> studentProfileAcademicActivity;
    private List<String> studentProfileCoCurricularActivity;
    private List<String> studentProfileExtraCurricularActivity;
    private List<String> studentProfileTutoringExperience;

    private List<ExternalLinkDto> studentProfileExternalLink;

    @Data
    public static class ExternalLinkDto {
        private String linkName;
        private String linkAddress;
    }
}
