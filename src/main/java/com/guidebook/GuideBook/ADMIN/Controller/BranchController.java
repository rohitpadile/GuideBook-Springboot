package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.dtos.AddBranchRequest;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeRequest;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.AlreadyPresentException;
import com.guidebook.GuideBook.ADMIN.exceptions.BranchNotFoundException;
import com.guidebook.GuideBook.ADMIN.Models.Branch;
import com.guidebook.GuideBook.ADMIN.Services.BranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com", "https://a.guidebookx.com",
        "https://13.235.131.222",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/admin/")
public class BranchController {
    private BranchService branchService;

    @Autowired
    BranchController(BranchService branchService){
        this.branchService = branchService;
    }
    @PostMapping("/addBranch")
    public ResponseEntity<Branch> addBranch(@RequestBody @Valid AddBranchRequest addBranchRequest)
            throws AlreadyPresentException {
        Branch addedBranch =  branchService.addBranch(addBranchRequest);
        return new ResponseEntity<>(addedBranch, HttpStatus.CREATED);
    }

    @PostMapping("/filteredBranches")
    public ResponseEntity<GetAllBranchNameListForCollegeResponse> getAllBranchNameListForCollege(
            @RequestBody @Valid GetAllBranchNameListForCollegeRequest request)
            throws BranchNotFoundException
    {
        GetAllBranchNameListForCollegeResponse res = branchService.getAllBranchNameListForCollege(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/getAllbranchNameList")
    public ResponseEntity<List<String>> getAllBranchNameList(){
        List<String> res = branchService.getAllBranchNameList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
