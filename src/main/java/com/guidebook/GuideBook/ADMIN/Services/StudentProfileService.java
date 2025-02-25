package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Repository.StudentRepository;
import com.guidebook.GuideBook.ADMIN.dtos.AddStudentProfileRequest;
import com.guidebook.GuideBook.ADMIN.dtos.GetStudentProfileResponse;
import com.guidebook.GuideBook.ADMIN.dtos.UpdateStudentProfileRequest;
import com.guidebook.GuideBook.ADMIN.dtos.UpdateStudentProfileSessionsPerWeekRequest;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.Repository.StudentProfileRepository;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentProfileService {

    private StudentProfileRepository studentProfileRepository;
    private final JwtUtil jwtUtil;
    private final StudentRepository studentRepository;
    @Autowired
    public StudentProfileService(StudentProfileRepository studentProfileRepository,
                                 JwtUtil jwtUtil,
                                 StudentRepository studentRepository) {
        this.studentProfileRepository = studentProfileRepository;
        this.jwtUtil = jwtUtil;
        this.studentRepository = studentRepository;
    }
    @Transactional
    public GetStudentProfileResponse addStudentProfile(AddStudentProfileRequest request)
    throws StudentProfileContentNotFoundException {
        Optional<StudentProfile> checkStudentProfile = studentProfileRepository.findStudentProfileByStudentWorkEmailIgnoreCase(request.getStudentWorkEmail());
        if(!checkStudentProfile.isPresent()){
            throw new StudentProfileContentNotFoundException("Student profile content not found at addStudentProfile() method");
        }
        StudentProfile studentProfile = getStudentProfile(request, checkStudentProfile);

        studentProfile = studentProfileRepository.save(studentProfile);

        return GetStudentProfileResponse.builder()
                .studentProfileAboutSection(studentProfile.getStudentProfileAboutSection())
                .studentProfileCityOfCoaching(studentProfile.getStudentProfileCityOfCoaching())
                .studentProfileExamScoreDetails(studentProfile.getStudentProfileExamScoreDetails())
                .studentProfileOtherExamScoreDetails(studentProfile.getStudentProfileOtherExamScoreDetails())
                .studentProfileActivityAndAchievements(studentProfile.getStudentProfileActivityAndAchievements())
                .studentProfileTutoringExperience(studentProfile.getStudentProfileTutoringExperience())
                .studentProfileExternalLinks(studentProfile.getStudentProfileExternalLinks())
                .studentProfileSessionsConducted(studentProfile.getStudentProfileSessionsConducted())
                .build();
    }
    @Transactional
    private static StudentProfile getStudentProfile(AddStudentProfileRequest request, Optional<StudentProfile> checkStudentProfile) {
        StudentProfile studentProfile = checkStudentProfile.get();

        if(request.getStudentProfileAboutSection()!=null)
            studentProfile.setStudentProfileAboutSection(request.getStudentProfileAboutSection());
        if(request.getStudentProfileCityOfCoaching()!=null)
            studentProfile.setStudentProfileCityOfCoaching(request.getStudentProfileCityOfCoaching());
        if(request.getStudentProfileExamScoreDetails()!=null)
            studentProfile.setStudentProfileExamScoreDetails(request.getStudentProfileExamScoreDetails());
        if(request.getStudentProfileOtherExamScoreDetails()!=null)
            studentProfile.setStudentProfileOtherExamScoreDetails(request.getStudentProfileOtherExamScoreDetails());
        if(request.getStudentProfileActivityAndAchievements()!=null)
            studentProfile.setStudentProfileActivityAndAchievements(request.getStudentProfileActivityAndAchievements());
//        if(request.getStudentProfileCoCurricularActivity()!=null)
//            studentProfile.setStudentProfileCoCurricularActivity(request.getStudentProfileCoCurricularActivity());
//        if(request.getStudentProfileExtraCurricularActivity()!=null)
//            studentProfile.setStudentProfileExtraCurricularActivity(request.getStudentProfileExtraCurricularActivity());
        if(request.getStudentProfileTutoringExperience()!=null)
            studentProfile.setStudentProfileTutoringExperience(request.getStudentProfileTutoringExperience());
        if(request.getStudentProfileExternalLink()!=null)
            studentProfile.setStudentProfileExternalLinks(request.getStudentProfileExternalLink());
        studentProfile.setStudentProfileSessionsConducted((long)0); //initial session count = 0L
        return studentProfile;
    }
    @Transactional
    public StudentProfile addStudentProfileWithAddStudent(StudentProfile studentProfile){
        return studentProfileRepository.save(studentProfile);
    } //this method create a profile whenever a new student is added
    //only mis is assigned

    public GetStudentProfileResponse getStudentProfile(String studentWorkEmail)
    throws StudentProfileContentNotFoundException {


         Optional<StudentProfile> checkProfile = studentProfileRepository.findStudentProfileByStudentWorkEmailIgnoreCase(studentWorkEmail);
         if(!checkProfile.isPresent()){
             throw new StudentProfileContentNotFoundException("Student profile content not found at getStudentProfile() method");
         }
         StudentProfile studentProfile = checkProfile.get();
         return GetStudentProfileResponse.builder()
                     .studentProfileAboutSection(studentProfile.getStudentProfileAboutSection())
                     .studentProfileCityOfCoaching(studentProfile.getStudentProfileCityOfCoaching())
                     .studentProfileExamScoreDetails(studentProfile.getStudentProfileExamScoreDetails())
                     .studentProfileOtherExamScoreDetails(studentProfile.getStudentProfileOtherExamScoreDetails())
                     .studentProfileActivityAndAchievements(studentProfile.getStudentProfileActivityAndAchievements())
                     .studentProfileTutoringExperience(studentProfile.getStudentProfileTutoringExperience())
                     .studentProfileExternalLinks(studentProfile.getStudentProfileExternalLinks())
                     .studentProfileSessionsConducted(studentProfile.getStudentProfileSessionsConducted())
                 .zoomSessionsPerWeek(studentProfile.getZoomSessionsPerWeek())
                 .zoomSessionsRemainingPerWeek(studentProfile.getZoomSessionsRemainingPerWeek())
                 .studentWorkEmail(studentWorkEmail)
                     .build();



        //private static method created for GetStudentProfileResponse DTO
    }
    @Transactional
    public GetStudentProfileResponse updateStudentProfile(String studentWorkEmail, UpdateStudentProfileRequest updateRequest)
    throws StudentProfileContentNotFoundException {

        Optional<StudentProfile> checkStudentProfile = studentProfileRepository.findStudentProfileByStudentWorkEmailIgnoreCase(studentWorkEmail);
        if(!checkStudentProfile.isPresent()){
            throw new StudentProfileContentNotFoundException("Student profile content not found at addStudentProfile() method");
        }
        StudentProfile studentProfile = getStudentProfile(updateRequest, checkStudentProfile);
        //DO NOT ASSIGN SESSIONS ATTENDED - CYBER ATTACKS CAN HAPPEN AND STUDENT SESSION COUNT CAN BE LOST

        studentProfile = studentProfileRepository.save(studentProfile);

        return GetStudentProfileResponse.builder()
                .studentProfileAboutSection(studentProfile.getStudentProfileAboutSection())
                .studentProfileCityOfCoaching(studentProfile.getStudentProfileCityOfCoaching())
                .studentProfileExamScoreDetails(studentProfile.getStudentProfileExamScoreDetails())
                .studentProfileOtherExamScoreDetails(studentProfile.getStudentProfileOtherExamScoreDetails())
                .studentProfileActivityAndAchievements(studentProfile.getStudentProfileActivityAndAchievements())
                .studentProfileTutoringExperience(studentProfile.getStudentProfileTutoringExperience())
                .studentProfileExternalLinks(studentProfile.getStudentProfileExternalLinks())
                .studentProfileSessionsConducted(studentProfile.getStudentProfileSessionsConducted())
                .build();
    }
    @Transactional
    private static StudentProfile getStudentProfile(UpdateStudentProfileRequest updateRequest, Optional<StudentProfile> checkStudentProfile) {
        StudentProfile studentProfile = checkStudentProfile.get();
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
        if(updateRequest.getStudentProfileActivityAndAchievements()!=null){
            studentProfile.setStudentProfileActivityAndAchievements(updateRequest.getStudentProfileActivityAndAchievements());
        }
        if(updateRequest.getStudentProfileTutoringExperience()!=null){
            studentProfile.setStudentProfileTutoringExperience(updateRequest.getStudentProfileTutoringExperience());
        }
        if(updateRequest.getStudentProfileExternalLinks()!=null){
            studentProfile.setStudentProfileExternalLinks(updateRequest.getStudentProfileExternalLinks());
        }
        return studentProfile;
    }

//    private static GetStudentProfileResponse getGetStudentProfileResponse(StudentProfile profile) {
//        GetStudentProfileResponse response = new GetStudentProfileResponse();
//
//        response.setStudentPublicEmail(profile.getStudentPublicEmail());
//        response.setStudentProfileAboutSection(profile.getStudentProfileAboutSection());
//        response.setStudentProfileCityOfCoaching(profile.getStudentProfileCityOfCoaching());
//        response.setStudentProfileExamScoreDetails(profile.getStudentProfileExamScoreDetails());
//        response.setStudentProfileOtherExamScoreDetails(profile.getStudentProfileOtherExamScoreDetails());
//        response.setStudentProfileActivityAndAchievements(profile.getStudentProfileActivityAndAchievements());
//        response.setStudentProfileCoCurricularActivity(profile.getStudentProfileCoCurricularActivity());
//        response.setStudentProfileExtraCurricularActivity(profile.getStudentProfileExtraCurricularActivity());
//        response.setStudentProfileTutoringExperience(profile.getStudentProfileTutoringExperience());
//        response.setStudentProfileSessionsConducted(profile.getStudentProfileSessionsConducted());
//        response.setStudentProfileExternalLinks(profile.getStudentProfileExternalLinks());
//        return response;
//    }

    public StudentProfile getStudentProfileForGeneralPurpose(String studentWorkEmail)
            throws StudentProfileContentNotFoundException {

        Optional<StudentProfile> checkProfile = studentProfileRepository.findStudentProfileByStudentWorkEmailIgnoreCase(studentWorkEmail);
        if(!checkProfile.isPresent()) {
            throw new StudentProfileContentNotFoundException("Student profile content not found at getStudentProfileForFeedbackFormSuccess() method");
        }
        return checkProfile.get();
    }

    public void updateStudentProfile(StudentProfile studentProfile)
    throws StudentProfileContentNotFoundException{
        studentProfileRepository.save(studentProfile);
    }

    public void deleteStudentProfile(StudentProfile profile){
        studentProfileRepository.delete(profile);
    }

    public StudentProfile getStudentProfileOptional(String studentWorkEmail)
            throws StudentProfileContentNotFoundException {

        Optional<StudentProfile> checkProfile = studentProfileRepository.findStudentProfileByStudentWorkEmailIgnoreCase(studentWorkEmail);
        if(checkProfile.isPresent()) {
            return checkProfile.get();
        }
        return null;
    }

    public Boolean checkIfStudentProfileExists(String studentWorkEmail){
        Optional<StudentProfile> checkProfile = studentProfileRepository.findStudentProfileByStudentWorkEmailIgnoreCase(studentWorkEmail);
        if(checkProfile.isPresent()) {
            return true;
        }
        return false;
    }

    public void updateStudentProfileSessionsPerWeek(
            UpdateStudentProfileSessionsPerWeekRequest request, String userEmail)
            throws StudentProfileContentNotFoundException
    {
        Optional<StudentProfile> check = studentProfileRepository.findStudentProfileByStudentWorkEmailIgnoreCase(userEmail);
        if(check.isPresent()){
            StudentProfile profile = check.get();
            profile.setZoomSessionsRemainingPerWeek(request.getNewSessionsRemainingPerWeekValue());
            profile.setZoomSessionsPerWeek(request.getNewSessionsPerWeekValue());
            studentProfileRepository.save(profile);
        } else {
            throw new StudentProfileContentNotFoundException("Student profile content not found at updateStudentProfileSessionsPerWeek() method");
        }
    }

    public List<StudentProfile> getAllStudentProfiles(){
        return studentProfileRepository.findAll();
    }
}
