
package com.guidebook.GuideBook.Models;


import com.guidebook.GuideBook.embeddables.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

//NOTE THE NAMING CONVENTION FOR EMBEDDEDABLE TABLES NAMES - KEEP IT CONSTANT EVERYWHERE IN THE APPLICATION
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long studentProfileId;
    Long studentMis;

    @ElementCollection
    @CollectionTable(name = "studentprofile_AboutSection", joinColumns = @JoinColumn(name = "studentProfileId"))
    List<AboutSection> studentProfileAboutSection; //List<String> is needed.
    //Displaying city of coaching will help students/parents to instantly get help from students
    //living in same city as that
    //Do not display name of coaching institute - that may voilate laws maybe. Just research on this.
    @ElementCollection
    @CollectionTable(name = "studentprofile_CityOfCoaching", joinColumns = @JoinColumn(name = "studentProfileId"))
    List<CityOfCoaching> studentProfileCityOfCoaching;
    @ElementCollection
    @CollectionTable(name = "studentprofile_ExamScoreDetails", joinColumns = @JoinColumn(name = "studentProfileId"))
    List<ExamScoreDetails> studentProfileExamScoreDetails; //maybe List<String> //TRY IF WRITING "<p></p>" that can directly send as html
    @ElementCollection
    @CollectionTable(name = "studentprofile_OtherExamScoreDetails", joinColumns = @JoinColumn(name = "studentProfileId"))
    List<OtherExamScoreDetails> studentProfileOtherExamScoreDetails;
    @ElementCollection
    @CollectionTable(name = "studentprofile_AcademicActivity", joinColumns = @JoinColumn(name = "studentProfileId"))
    List<AcademicActivity> studentProfileAcademicActivity; //activity included achievements too
    @ElementCollection
    @CollectionTable(name = "studentprofile_CoCurricularActivity", joinColumns = @JoinColumn(name = "studentProfileId"))
    List<CoCurricularActivity> studentProfileCoCurricularActivity;
    @ElementCollection
    @CollectionTable(name = "studentprofile_ExtraCurricularActivity", joinColumns = @JoinColumn(name = "studentProfileId"))
    List<ExtraCurricularActivity> studentProfileExtraCurricularActivity;
    @ElementCollection
    @CollectionTable(name = "studentprofile_TutoringExperience", joinColumns = @JoinColumn(name = "studentProfileId"))
    List<TutoringExperience> studentProfileTutoringExperience;
    @ElementCollection
    @CollectionTable(name = "studentprofile_ExternalLink", joinColumns = @JoinColumn(name = "studentProfileId"))
    List<ExternalLink> studentProfileExternalLink;

    static Long studentProfileSessionsConducted; //Sessions conducted on our platform

}
//**studentAboutSection:-
    //IMPORTANT: "I Offer customized study plans and stress management techniques",
    // written like websites shows comments of people that have used their websites
    // like freecodecamp does on their websites
    //more "studyPlan": "I offer Detailed weekly study plan focusing on concept clarity and practice"
    //So a comment should be saying what exactly the student can tell you clearly about in the session
    //example: "I offer best Textbook suggestions, coaching suggestions"

//**studentCityOfCoaching

//**studentScoreDetails - INCLUDE WHATEVER SCORE THE STUDENT WANT TO INCLUDE
//        "jeeRank": 1500,
//        "jeeScore": 320,
//        "Physics": 110, say - "I scored highest in physics./ I love physics"
//        "Chemistry": 105, - "I scored highest in chemistry./ I love chemistry"
//        "Mathematics": 105 - "I scored highest in mathematics./ I love mathematics"

//**studentOtherExamScoreDetails
//        "otherExamsQualified": ["BITSAT - 350", "VITEEE - Rank 200"],

//**studentAcademicActivity, studentCoCurricularActivity, studentExtraCurricularAchievements,
//        "leadershipRoles": ["President of Robotics Club", "Organized National Level Tech Fest"],
//        "competitions": ["Won 1st Prize in National Science Olympiad", "2nd Place in Coding Competition"],
//        "workshopsConducted": ["How to Crack JEE", "Stress Management during Exams"],

//**studentTutoringExperience
//        "tutoringExperience": "2 years of tutoring JEE aspirants",

//**Refer LinkedIn Profile for more
//        "internships": ["Intern at Siemens", "Workshop on Robotics"], //REFER LINKEDIN