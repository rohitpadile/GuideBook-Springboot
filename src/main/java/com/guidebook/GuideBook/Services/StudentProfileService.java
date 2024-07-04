package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.Repository.StudentProfileRepository;
import com.guidebook.GuideBook.dtos.AddStudentProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guidebook.GuideBook.embeddables.*;
import java.util.stream.Collectors;

@Service
public class StudentProfileService {

    private StudentProfileRepository studentProfileRepository;
    @Autowired
    public StudentProfileService(StudentProfileRepository studentProfileRepository) {
        this.studentProfileRepository = studentProfileRepository;
    }

    public StudentProfile addStudentProfile(AddStudentProfileRequest request) {
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setStudentMis(request.getStudentMis());
        studentProfile.setStudentProfileAboutSection(
                request.getStudentProfileAboutSection().stream()
                        .map(AboutSection::new).collect(Collectors.toList())
        );
        studentProfile.setStudentProfileCityOfCoaching(
                request.getStudentProfileCityOfCoaching().stream()
                        .map(CityOfCoaching::new).collect(Collectors.toList())
        );
        studentProfile.setStudentProfileExamScoreDetails(
                request.getStudentProfileExamScoreDetails().stream()
                        .map(ExamScoreDetails::new).collect(Collectors.toList())
        );
        studentProfile.setStudentProfileOtherExamScoreDetails(
                request.getStudentProfileOtherExamScoreDetails().stream()
                        .map(OtherExamScoreDetails::new).collect(Collectors.toList())
        );
        studentProfile.setStudentProfileAcademicActivity(
                request.getStudentProfileAcademicActivity().stream()
                        .map(AcademicActivity::new).collect(Collectors.toList())
        );
        studentProfile.setStudentProfileCoCurricularActivity(
                request.getStudentProfileCoCurricularActivity().stream()
                        .map(CoCurricularActivity::new).collect(Collectors.toList())
        );
        studentProfile.setStudentProfileExtraCurricularActivity(
                request.getStudentProfileExtraCurricularActivity().stream()
                        .map(ExtraCurricularActivity::new).collect(Collectors.toList())
        );
        studentProfile.setStudentProfileTutoringExperience(
                request.getStudentProfileTutoringExperience().stream()
                        .map(TutoringExperience::new).collect(Collectors.toList())
        );
        studentProfile.setStudentProfileExternalLink(
                request.getStudentProfileExternalLink().stream()
                        .map(link -> new ExternalLink(link.getLinkName(), link.getLinkAddress()))
                        .collect(Collectors.toList())
        );

        return studentProfileRepository.save(studentProfile);
    }

//    public StudentProfile addStudentProfile(StudentProfile studentProfile){
//        return studentProfileRepository.save(studentProfile);
//    }
}
