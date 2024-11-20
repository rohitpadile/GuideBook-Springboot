package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.dtos.AddMoreBranchesToCollegeRequest;
import com.guidebook.GuideBook.ADMIN.dtos.GetCollegeListForExamResponse;
import com.guidebook.GuideBook.ADMIN.Services.CollegeService;
import com.guidebook.GuideBook.ADMIN.dtos.AddCollegeRequest;
//import com.guidebook.GuideBook.dtos.GetAllCollegeListForClubsResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.BranchNotFoundException;
import com.guidebook.GuideBook.ADMIN.exceptions.EntranceExamNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com", "https://a.guidebookx.com",
        "https://13.235.131.222",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/admin/")
public class CollegeController {
    private CollegeService collegeService;
    @Autowired
    CollegeController(CollegeService collegeService){
        this.collegeService = collegeService;
    }
    @GetMapping("/collegesForExam/{examName}")
    public ResponseEntity<GetCollegeListForExamResponse> getCollegeListForExamRequest(
            @PathVariable String examName
    ){
        GetCollegeListForExamResponse getCollegeListForExamResponse =
                collegeService.getCollegeListForExamRequest(examName);
        return new ResponseEntity<>(getCollegeListForExamResponse, HttpStatus.OK);
    }


    @PostMapping("/addCollegeWithBranches")
    public ResponseEntity<Void> addCollegeWithBranches(@RequestBody @Valid AddCollegeRequest addCollegeRequest)
            throws EntranceExamNotFoundException,
            BranchNotFoundException {
        collegeService.addCollegeWithBranches(addCollegeRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/addMoreBranchesToCollege")
    @Transactional
    public ResponseEntity<Void> addMoreBranchesToCollege(@RequestBody AddMoreBranchesToCollegeRequest request){
        collegeService.addMoreBranchesToCollege(request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


//    @GetMapping("/collegesForExam")
//    public ResponseEntity<GetAllCollegeListForClubsResponse> getCollegesForClubs(){
//        GetAllCollegeListForClubsResponse res = collegeService.getCollegesForClubs();
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }

}
