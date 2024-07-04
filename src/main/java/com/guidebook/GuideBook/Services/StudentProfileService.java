package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.Repository.StudentProfileRepository;
import com.guidebook.GuideBook.dtos.AddStudentProfileRequest;
import com.guidebook.GuideBook.dtos.GetStudentProfileResponse;
import com.guidebook.GuideBook.exceptions.StudentProfileContentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guidebook.GuideBook.embeddables.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentProfileService {

    private StudentProfileRepository studentProfileRepository;
    @Autowired
    public StudentProfileService(StudentProfileRepository studentProfileRepository) {
        this.studentProfileRepository = studentProfileRepository;
    }

    public StudentProfile addStudentProfile(AddStudentProfileRequest request) {
        StudentProfile studentProfile = studentProfileRepository.findStudentProfileByStudentMis(request.getStudentMis());
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

    public StudentProfile addStudentProfileWithAddStudent(StudentProfile studentProfile){
        return studentProfileRepository.save(studentProfile);
    } //this method create a profile whenever a new student is added
    //only mis is assigned
    public GetStudentProfileResponse getStudentProfile(Long studentMis)
    throws StudentProfileContentNotFoundException {

        StudentProfile studentProfile =
                studentProfileRepository.findStudentProfileByStudentMis(studentMis);
        GetStudentProfileResponse response = new GetStudentProfileResponse();
        response.setStudentMis(studentProfile.getStudentMis());
        response.setStudentProfileAboutSection(studentProfile.getStudentProfileAboutSection().stream().map(AboutSection::getAbout).collect(Collectors.toList()));
        response.setStudentProfileCityOfCoaching(studentProfile.getStudentProfileCityOfCoaching().stream().map(CityOfCoaching::getCityOfCoaching).collect(Collectors.toList()));
        response.setStudentProfileExamScoreDetails(studentProfile.getStudentProfileExamScoreDetails().stream().map(ExamScoreDetails::getScoreDetail).collect(Collectors.toList()));
        response.setStudentProfileOtherExamScoreDetails(studentProfile.getStudentProfileOtherExamScoreDetails().stream().map(OtherExamScoreDetails::getOtherScoreDetail).collect(Collectors.toList()));
        response.setStudentProfileAcademicActivity(studentProfile.getStudentProfileAcademicActivity().stream().map(AcademicActivity::getActivity).collect(Collectors.toList()));
        response.setStudentProfileCoCurricularActivity(studentProfile.getStudentProfileCoCurricularActivity().stream().map(CoCurricularActivity::getActivity).collect(Collectors.toList()));
        response.setStudentProfileExtraCurricularActivity(studentProfile.getStudentProfileExtraCurricularActivity().stream().map(ExtraCurricularActivity::getActivity).collect(Collectors.toList()));
        response.setStudentProfileTutoringExperience(studentProfile.getStudentProfileTutoringExperience().stream().map(TutoringExperience::getExperience).collect(Collectors.toList()));
        response.setStudentProfileExternalLink(studentProfile.getStudentProfileExternalLink());
        response.setStudentProfileSessionsConducted(StudentProfile.studentProfileSessionsConducted);

        return response;
    }

}
