package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Services.AdminBranchService;
import com.guidebook.GuideBook.Services.AdminCollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/" )
@RestController
@RequestMapping("/api/v1/admin/")
public class AdminBranchController {
    @Autowired
    private AdminBranchService adminBranchService;

    @GetMapping("/branches")
    public List<Branch> getAllBranches() {
        return adminBranchService.getAllBranches();
    }

    @PostMapping("/addBranch")
    public Branch addBranch(@RequestBody Branch branch) {
        return adminBranchService.saveBranch(branch);
    }
}