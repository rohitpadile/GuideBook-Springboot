package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Models.StudentClassType;
import com.guidebook.GuideBook.Services.BranchService;
import com.guidebook.GuideBook.dtos.AddBranchRequest;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllBranchNameListResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class BranchController {
    private BranchService branchService;

    @Autowired
    BranchController(BranchService branchService){
        this.branchService = branchService;
    }
    @PostMapping("/addBranch")
    public ResponseEntity<Branch> addBranch(@RequestBody @Valid AddBranchRequest addBranchRequest){

        Branch addedBranch =  branchService.addBranch(addBranchRequest);
        return new ResponseEntity<>(addedBranch, HttpStatus.CREATED);
    }

    @GetMapping("/branches")
    public ResponseEntity<GetAllBranchNameListResponse> getAllbranchNamesList(){
        GetAllBranchNameListResponse res = branchService.getAllbranchNamesList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
//
//    Yeah no errors but where are the drop drop list for those 5 filters?
//        And also we need to create a method which dynamically creates the dto for
//
//@Data
//public class FilteredStudentListRequest {//edit this dto after reading the minor project code.
//    @NotEmpty(message = "Branch name cannot be empty")
//    private String branchName;
//
//    @Min(value = 0, message = "Minimum grade should not be less than 0")
//    private Double minGrade;
//
//    @Min(value = 0, message = "Minimum CET percentile should not be less than 0")
//    private Double minCetPercentile;
//
//    @NotNull(message = "Student class type cannot be null")
//    private StudentClassType studentClassType;
//
//    @NotEmpty(message = "Language name cannot be empty")
//    private String languageName;
//}
//
//
//
//    And sends it to backend and fetches the list of student names
//
//@Slf4j
//@Data
//public class FilteredStudentListResponse {
//    @NotNull(message = "Student names list cannot be empty")
//    private List<String> studentNameList;
//}
//
//    which are displayed on the page below the circle profile photo right!
//
//        And the content in the drop down list is to be fetched from api only
//        I will give you the apis for that, and the value in the drop down list selected dynamically goes to the dto FilteredStudentListRequest
//
//
