//package com.guidebook.GuideBook.ADMIN.Services;
//
//import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
//import com.guidebook.GuideBook.ADMIN.Repository.StudentProfileRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class ScheduledTasksForStudentProfile {
//    private final StudentProfileService studentProfileService;
//    private final StudentProfileRepository studentProfileRepository;
//    @Autowired
//    public ScheduledTasksForStudentProfile(StudentProfileService studentProfileService,
//                                           StudentProfileRepository studentProfileRepository) {
//        this.studentProfileService = studentProfileService;
//        this.studentProfileRepository = studentProfileRepository;
//    }
//
//    @Scheduled(fixedRate = 3600000) //Change this to 3600000 later //using it for production use now
//    public void resetWeeklySessions() {
//        // Fetch all student profiles or filter as needed
//        List<StudentProfile> studentProfiles = studentProfileService.getAllStudentProfiles();
//        for (StudentProfile studentProfile : studentProfiles) {
//            resetWeeklySessionsIfNeeded(studentProfile);
//        }
//    }
//
//    // Existing method
//    private void resetWeeklySessionsIfNeeded(StudentProfile studentProfile) {
//        // Get current date from database
//        Date currentDate = studentProfileRepository.getCurrentDatabaseDate();
//        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//        // Check if today is Monday
//        if (localDate.getDayOfWeek() == DayOfWeek.MONDAY) {
//            // Reset the zoomSessionsPerWeek count to initial value
//            studentProfile.setZoomSessionsRemainingPerWeek(studentProfile.getZoomSessionsPerWeek());
//            studentProfileRepository.save(studentProfile);
//        }
//    }
//}
