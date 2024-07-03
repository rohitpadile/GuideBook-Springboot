
package com.guidebook.GuideBook.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

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
    String studentCityOfCoaching;
    String studentAboutSection; //List<String> is needed.
    //Displaying city of coaching will help students/parents to instantly get help from students
    //living in same city as that
    //Do not display name of coaching institute - that may voilate laws maybe. Just research on this.
    String studentScoreDetails;
    String studentOtherExamScoreDetails;

    String studentAcademicActivity; //activity included achievements too
    String studentCoCurricularActivity;
    String studentExtraCurricularAchievements;
    String studentTutoringExperience;
    Long studentSessionsConducted; //Sessions conducted on our platform

}
//**studentAboutSection:-
    //IMPORTANT: "I Offer customized study plans and stress management techniques",
    // written like websites shows comments of people that have used their websites
    // like freecodecamp does on their websites
    //more "studyPlan": "I offer Detailed weekly study plan focusing on concept clarity and practice"
    //So a comment should be saying what exactly the student can tell you clearly about in the session
    //example: "I offer best Textbook suggestions, coaching suggestions"

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