package com.guidebook.GuideBook.TR.Controller;

import com.guidebook.GuideBook.TR.Services.TRBranchService;
import com.guidebook.GuideBook.TR.dtos.TRGetAllBranchNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/teamRecruiter/")
public class TRBranchController {
    private TRBranchService trBranchService;
    @Autowired
    public TRBranchController(TRBranchService trBranchService) {
        this.trBranchService = trBranchService;
    }

    @GetMapping("getAllBranches")
    public ResponseEntity<TRGetAllBranchNameResponse> getAllBranchNames(){
        TRGetAllBranchNameResponse res = trBranchService.getAllBranchNames();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
