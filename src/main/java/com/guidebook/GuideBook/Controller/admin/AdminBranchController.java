package com.guidebook.GuideBook.Controller.admin;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Services.admin.AdminBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class AdminBranchController {
    private AdminBranchService adminBranchService;

    @Autowired
    AdminBranchController(AdminBranchService adminBranchService){
        this.adminBranchService = adminBranchService;
    }

    @GetMapping("/branches")
    public List<Branch> getAllBranches() {
        return adminBranchService.getAllBranches();
    }

    @PostMapping("/addBranch")
    public Branch addBranch(@RequestBody Branch branch) {
        Branch savedBranch = adminBranchService.addBranch(branch);
        return ResponseEntity.ok(savedBranch).getBody();
    }

    @PostMapping("/addBranchList")
    public Branch addBranch(@RequestBody List<Branch> branchList) {
        Branch savedBranch = null;
        for(Branch branch : branchList){
            savedBranch = adminBranchService.addBranch(branch);
        }
        return ResponseEntity.ok(savedBranch).getBody();
    }
    // Update an existing branch by branchId
    @PutMapping("/updateBranch/{branchId}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long branchId, @RequestBody Branch branchDetails) {
        Branch updatedBranch = adminBranchService.updateBranch(branchId, branchDetails);
        return ResponseEntity.ok(updatedBranch);
    }

    // Delete a branch by branchId
    @DeleteMapping("/deleteBranch/{branchId}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long branchId) {
        adminBranchService.deleteBranch(branchId);
        return ResponseEntity.noContent().build();
    }
}