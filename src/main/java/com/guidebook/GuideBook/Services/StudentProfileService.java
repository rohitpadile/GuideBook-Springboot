package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.Repository.StudentProfileRepository;
import com.guidebook.GuideBook.dtos.AddStudentProfileRequest;
import com.guidebook.GuideBook.dtos.GetStudentProfileResponse;
import com.guidebook.GuideBook.dtos.UpdateStudentProfileRequest;
import com.guidebook.GuideBook.exceptions.StudentProfileContentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileService {

    private StudentProfileRepository studentProfileRepository;
    @Autowired
    public StudentProfileService(StudentProfileRepository studentProfileRepository) {
        this.studentProfileRepository = studentProfileRepository;
    }

    public GetStudentProfileResponse addStudentProfile(AddStudentProfileRequest request)
    throws StudentProfileContentNotFoundException{
        StudentProfile studentProfile = studentProfileRepository.findStudentProfileByStudentWorkEmail(request.getStudentWorkEmail());

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
            studentProfile.setStudentProfileExternalLinks(request.getStudentProfileExternalLink());
        studentProfile.setStudentProfileSessionsConducted((long)0);

        return getGetStudentProfileResponse(
                studentProfileRepository.save(studentProfile)
        );
    }

    public StudentProfile addStudentProfileWithAddStudent(StudentProfile studentProfile){
        return studentProfileRepository.save(studentProfile);
    } //this method create a profile whenever a new student is added
    //only mis is assigned

    public GetStudentProfileResponse getStudentProfile(String studentWorkEmail)
    throws StudentProfileContentNotFoundException {

        return getGetStudentProfileResponse(
                studentProfileRepository.findStudentProfileByStudentWorkEmail(studentWorkEmail)
        );
        //private static method created for GetStudentProfileResponse DTO
    }

    public GetStudentProfileResponse updateStudentProfile(String studentWorkEmail, UpdateStudentProfileRequest updateRequest)
    throws StudentProfileContentNotFoundException {

        StudentProfile studentProfile = studentProfileRepository.findStudentProfileByStudentWorkEmail(studentWorkEmail);

        // Update fields based on the DTO
        if(updateRequest.getStudentPublicEmail()!=null){
            studentProfile.setStudentPublicEmail(updateRequest.getStudentPublicEmail());
        }
        if(updateRequest.getStudentProfileAboutSection() != null){
            studentProfile.setStudentProfileAboutSection(updateRequest.getStudentProfileAboutSection());
        }
        if(updateRequest.getStudentProfileCityOfCoaching()!=null){
            studentProfile.setStudentProfileCityOfCoaching(updateRequest.getStudentProfileCityOfCoaching());
        }
        if(updateRequest.getStudentProfileExamScoreDetails()!=null){
            studentProfile.setStudentProfileExamScoreDetails(updateRequest.getStudentProfileExamScoreDetails());
        }
        if(updateRequest.getStudentProfileOtherExamScoreDetails()!=null){
            studentProfile.setStudentProfileOtherExamScoreDetails(updateRequest.getStudentProfileOtherExamScoreDetails());
        }
        if(updateRequest.getStudentProfileAcademicActivity()!=null){
            studentProfile.setStudentProfileAcademicActivity(updateRequest.getStudentProfileAcademicActivity());
        }
        if(updateRequest.getStudentProfileCoCurricularActivity()!=null){
            studentProfile.setStudentProfileCoCurricularActivity(updateRequest.getStudentProfileCoCurricularActivity());
        }
        if(updateRequest.getStudentProfileExtraCurricularActivity()!=null){
            studentProfile.setStudentProfileExtraCurricularActivity(updateRequest.getStudentProfileExtraCurricularActivity());
        }
        if(updateRequest.getStudentProfileTutoringExperience()!=null){
            studentProfile.setStudentProfileTutoringExperience(updateRequest.getStudentProfileTutoringExperience());
        }
        if(updateRequest.getStudentProfileExternalLinks()!=null){
            studentProfile.setStudentProfileExternalLinks(updateRequest.getStudentProfileExternalLinks());
        }
        //DO NOT ASSIGN SESSIONS ATTENDED - CYBER ATTACKS CAN HAPPEN AND STUDENT SESSION COUNT CAN BE LOST

        return getGetStudentProfileResponse(
                studentProfileRepository.save(studentProfile)
        );

    }

    private static GetStudentProfileResponse getGetStudentProfileResponse(StudentProfile profile) {
        GetStudentProfileResponse response = new GetStudentProfileResponse();

        response.setStudentPublicEmail(profile.getStudentPublicEmail());
        response.setStudentProfileAboutSection(profile.getStudentProfileAboutSection());
        response.setStudentProfileCityOfCoaching(profile.getStudentProfileCityOfCoaching());
        response.setStudentProfileExamScoreDetails(profile.getStudentProfileExamScoreDetails());
        response.setStudentProfileOtherExamScoreDetails(profile.getStudentProfileOtherExamScoreDetails());
        response.setStudentProfileAcademicActivity(profile.getStudentProfileAcademicActivity());
        response.setStudentProfileCoCurricularActivity(profile.getStudentProfileCoCurricularActivity());
        response.setStudentProfileExtraCurricularActivity(profile.getStudentProfileExtraCurricularActivity());
        response.setStudentProfileTutoringExperience(profile.getStudentProfileTutoringExperience());
        response.setStudentProfileSessionsConducted(profile.getStudentProfileSessionsConducted());
        response.setStudentProfileExternalLinks(profile.getStudentProfileExternalLinks());
        return response;
    }

}
