//package com.guidebook.GuideBook.Controller.teamRecruiter;
//
//import com.guidebook.GuideBook.Controller.StudentController;
//import com.guidebook.GuideBook.Models.Student;
//import com.guidebook.GuideBook.Services.StudentService;
//import com.guidebook.GuideBook.dtos.AddStudentRequest;
//import com.guidebook.GuideBook.dtos.GetStudentBasicDetailsResponse;
//import com.guidebook.GuideBook.dtos.UpdateStudentRequest;
//import com.guidebook.GuideBook.dtos.filterstudents.FilteredStudentDetails;
//import com.guidebook.GuideBook.dtos.filterstudents.FilteredStudentListRequest;
//import com.guidebook.GuideBook.exceptions.*;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = {
//        "http://guidebookx.s3-website.ap-south-1.amazonaws.com",
//        "https://guidebookx.s3-website.ap-south-1.amazonaws.com",
//        "http://d23toh43udoeld.cloudfront.net",
//        "https://d23toh43udoeld.cloudfront.net",
//        "http://guidebookx.com",
//        "https://guidebookx.com", "http://localhost:3000", "http://localhost:8080",
//        "https://www.guidebookx.com"})
//@RestController
//@RequestMapping("/api/v1/teamRecruiter/")
//public class StudentControllerTeamRecruiter {
//
//    private StudentService studentService;
//    private StudentController studentController;
//    @Autowired
//    StudentControllerTeamRecruiter(StudentService studentService,
//                                   StudentController studentController){
//        this.studentService = studentService;
//        this.studentController = studentController;
//    }
//
//
//    @PostMapping("/addStudent")
//    public ResponseEntity<GetStudentBasicDetailsResponse> addStudent(@RequestBody @Valid AddStudentRequest addStudentRequest)
//            throws CollegeNotFoundException,
//            BranchNotFoundException,
//            StudentClassTypeNotFoundException,
//            StudentCategoryNotFoundException,
//            LanguageNotFoundException
//    {
//        return studentController.addStudent(addStudentRequest);
//    }
//
//    @GetMapping("/studentBasicDetails/{workemail}")
//    public ResponseEntity<GetStudentBasicDetailsResponse> getStudentBasicDetails(@PathVariable String workemail)
//            throws StudentBasicDetailsNotFoundException
//    {
//        return studentController.getStudentBasicDetails(workemail);
//    }
//
//    @PostMapping("/updateStudent")
//    public ResponseEntity<GetStudentBasicDetailsResponse> updateStudent(@RequestBody @Valid UpdateStudentRequest updateStudentRequest)
//            throws StudentClassTypeNotFoundException, CollegeNotFoundException, StudentCategoryNotFoundException, LanguageNotFoundException {
//        return studentController.updateStudent(updateStudentRequest);
//    }
//
//
//    //ABOVE ARE THE SAME METHODS THAT ADMIN ALSO USE - LIKE ME
//
//    //BELOW METHODS TO BE DEFINED ARE :-
//
//
//}
