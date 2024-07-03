
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
}

//        "jeeRank": 1500,
//        "jeeScore": 320,
//        "coachingInstitute": "Resonance",
//        "mockTestScores": {
//        "Physics": 110,
//        "Chemistry": 105,
//        "Mathematics": 105
//        },
//        "studyPlan": "Detailed weekly study plan focusing on concept clarity and practice",

//        "otherExamsQualified": ["BITSAT - 350", "VITEEE - Rank 200"],
//        "researchProjects": ["Project on Renewable Energy", "Paper on Electric Vehicles"],
//        "internships": ["Intern at Siemens", "Workshop on Robotics"],
//        "leadershipRoles": ["President of Robotics Club", "Organized National Level Tech Fest"],
//        "competitions": ["Won 1st Prize in National Science Olympiad", "2nd Place in Coding Competition"],
//        "tutoringExperience": "2 years of tutoring JEE aspirants",
//        "workshopsConducted": ["How to Crack JEE", "Stress Management during Exams"],

//IMPORTANT: "I Offer customized study plans and stress management techniques", written like websites shows comments of people that have used their websites like freecodecamp
//Give a personalize experience on each page - context in each page are differnt

