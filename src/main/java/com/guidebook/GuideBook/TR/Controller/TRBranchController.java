package com.guidebook.GuideBook.TR.Controller;

import com.guidebook.GuideBook.ADMIN.Controller.BranchController;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeRequest;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.BranchNotFoundException;
import com.guidebook.GuideBook.TR.Services.TRBranchService;
import com.guidebook.GuideBook.TR.dtos.TRGetAllBranchNameResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/teamRecruiter/")
public class TRBranchController {
    private TRBranchService trBranchService;
    private BranchController branchController;
    @Autowired
    public TRBranchController(TRBranchService trBranchService) {
        this.trBranchService = trBranchService;
    }

    @GetMapping("getAllBranches")
    public ResponseEntity<TRGetAllBranchNameResponse> getAllBranchNames(){
        TRGetAllBranchNameResponse res = trBranchService.getAllBranchNames();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PostMapping("/filteredBranchesForCollege")
    public ResponseEntity<GetAllBranchNameListForCollegeResponse> getAllBranchNameListForCollege(
            @RequestBody @Valid GetAllBranchNameListForCollegeRequest request)
            throws BranchNotFoundException
    {
        return branchController.getAllBranchNameListForCollege(request);
    }
}
