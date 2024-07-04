package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.Repository.StudentProfileRepository;
import com.guidebook.GuideBook.dtos.AddStudentProfileRequest;
import com.guidebook.GuideBook.dtos.GetStudentProfileResponse;
import com.guidebook.GuideBook.dtos.UpdateStudentProfileRequest;
import com.guidebook.GuideBook.exceptions.StudentProfileContentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guidebook.GuideBook.embeddables.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentProfileService {

    private StudentProfileRepository studentProfileRepository;
    @Autowired
    public StudentProfileService(StudentProfileRepository studentProfileRepository) {
        this.studentProfileRepository = studentProfileRepository;
    }

    public GetStudentProfileResponse addStudentProfile(AddStudentProfileRequest request)
    throws StudentProfileContentNotFoundException{
        StudentProfile studentProfile = studentProfileRepository.findStudentProfileByStudentMis(request.getStudentMis());

        if(request.getStudentProfileAboutSection()!=null)
            studentProfile.setStudentProfileAboutSection(request.getStudentProfileAboutSection());

        if(request.getStudentProfileCityOfCoaching()!=null)
            studentProfile.setStudentProfileCityOfCoaching(request.getStudentProfileCityOfCoaching());
        if(request.getStudentProfileExamScoreDetails()!=null)
            studentProfile.setStudentProfileExamScoreDetails(request.getStudentProfileExamScoreDetails());
        if(request.getStudentProfileOtherExamScoreDetails()!=null)
            studentProfile.setStudentProfileOtherExamScoreDetails(request.getStudentProfileOtherExamScoreDetails());
        if(request.getStudentProfileAcademicActivity()!=null)
            studentProfile.setStudentProfileAcademicActivity(request.getStudentProfileAcademicActivity());
        if(request.getStudentProfileCoCurricularActivity()!=null)
            studentProfile.setStudentProfileCoCurricularActivity(request.getStudentProfileCoCurricularActivity());
        if(request.getStudentProfileExtraCurricularActivity()!=null)
            studentProfile.setStudentProfileExtraCurricularActivity(request.getStudentProfileExtraCurricularActivity());
        if(request.getStudentProfileTutoringExperience()!=null)
            studentProfile.setStudentProfileTutoringExperience(request.getStudentProfileTutoringExperience());
        if(request.getStudentProfileExternalLink()!=null)
            studentProfile.setStudentProfileExternalLink(request.getStudentProfileExternalLink());
        studentProfile.setStudentProfileSessionsConducted((long)0);


        GetStudentProfileResponse response = new GetStudentProfileResponse();
        StudentProfile savedProfile = studentProfileRepository.save(studentProfile);
        response.setStudentMis(savedProfile.getStudentMis());
        response.setStudentProfileAboutSection(savedProfile.getStudentProfileAboutSection());
        response.setStudentProfileCityOfCoaching(savedProfile.getStudentProfileCityOfCoaching());
        response.setStudentProfileExamScoreDetails(savedProfile.getStudentProfileExamScoreDetails());
        response.setStudentProfileOtherExamScoreDetails(savedProfile.getStudentProfileOtherExamScoreDetails());
        response.setStudentProfileAcademicActivity(savedProfile.getStudentProfileAcademicActivity());
        response.setStudentProfileCoCurricularActivity(savedProfile.getStudentProfileCoCurricularActivity());
        response.setStudentProfileExtraCurricularActivity(savedProfile.getStudentProfileExtraCurricularActivity());
        response.setStudentProfileTutoringExperience(savedProfile.getStudentProfileTutoringExperience());
        response.setStudentProfileExternalLink(savedProfile.getStudentProfileExternalLink());
        savedProfile.setStudentProfileSessionsConducted(savedProfile.getStudentProfileSessionsConducted());
        return response;
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
        response.setStudentProfileAboutSection(studentProfile.getStudentProfileAboutSection());
        response.setStudentProfileCityOfCoaching(studentProfile.getStudentProfileCityOfCoaching());
        response.setStudentProfileExamScoreDetails(studentProfile.getStudentProfileExamScoreDetails());
        response.setStudentProfileOtherExamScoreDetails(studentProfile.getStudentProfileOtherExamScoreDetails());
        response.setStudentProfileAcademicActivity(studentProfile.getStudentProfileAcademicActivity());
        response.setStudentProfileCoCurricularActivity(studentProfile.getStudentProfileCoCurricularActivity());
        response.setStudentProfileExtraCurricularActivity(studentProfile.getStudentProfileExtraCurricularActivity());
        response.setStudentProfileTutoringExperience(studentProfile.getStudentProfileTutoringExperience());
        response.setStudentProfileExternalLink(studentProfile.getStudentProfileExternalLink());
        response.setStudentProfileSessionsConducted(studentProfile.getStudentProfileSessionsConducted());

        return response;
    }

    public StudentProfile updateStudentProfile(Long studentMis, UpdateStudentProfileRequest updateRequest) {

        StudentProfile studentProfile = studentProfileRepository.findStudentProfileByStudentMis(studentMis);

        // Update fields based on the DTO
        if(updateRequest.getAboutSection() != null){
            studentProfile.setStudentProfileAboutSection(updateRequest.getAboutSection());
        }
        if(updateRequest.getCityOfCoaching()!=null){
            studentProfile.setStudentProfileCityOfCoaching(updateRequest.getCityOfCoaching());
        }
        if(updateRequest.getExamScoreDetails()!=null){
            studentProfile.setStudentProfileExamScoreDetails(updateRequest.getExamScoreDetails());
        }
        if(updateRequest.getOtherExamScoreDetails()!=null){
            studentProfile.setStudentProfileOtherExamScoreDetails(updateRequest.getOtherExamScoreDetails());
        }
        if(updateRequest.getAcademicActivity()!=null){
            studentProfile.setStudentProfileAcademicActivity(updateRequest.getAcademicActivity());
        }
        if(updateRequest.getCoCurricularActivity()!=null){
            studentProfile.setStudentProfileCoCurricularActivity(updateRequest.getCoCurricularActivity());
        }
        if(updateRequest.getExtraCurricularActivity()!=null){
            studentProfile.setStudentProfileExtraCurricularActivity(updateRequest.getExtraCurricularActivity());
        }
        if(updateRequest.getTutoringExperience()!=null){
            studentProfile.setStudentProfileTutoringExperience(updateRequest.getTutoringExperience());
        }
        if(updateRequest.getExternalLinks()!=null){
            studentProfile.setStudentProfileExternalLink(updateRequest.getExternalLinks());
        }
        return studentProfileRepository.save(studentProfile);
    }

}
