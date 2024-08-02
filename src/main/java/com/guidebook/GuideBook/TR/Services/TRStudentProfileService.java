//package com.guidebook.GuideBook.TR.Services;
//
//import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
//import com.guidebook.GuideBook.ADMIN.Repository.StudentProfileRepository;
//import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//@Service
//public class TRStudentProfileService {
//    private StudentProfileRepository studentProfileRepository;
//    @Autowired
//    public TRStudentProfileService(StudentProfileRepository studentProfileRepository) {
//        this.studentProfileRepository = studentProfileRepository;
//    }
//
//    public StudentProfile getStudentProfileOptional(String studentWorkEmail)
//            throws StudentProfileContentNotFoundException {
//
//        Optional<StudentProfile> checkProfile = studentProfileRepository.findStudentProfileByStudentWorkEmail(studentWorkEmail);
//        if(checkProfile.isPresent()) {
//            return checkProfile.get();
//        }
//        return null;
//    }
//}
