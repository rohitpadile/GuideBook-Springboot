package com.guidebook.GuideBook.Controller.admin;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Services.admin.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
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

    @GetMapping("/branches")
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> res =  branchService.getAllBranches();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/addBranch")
    public ResponseEntity<Branch> addBranch(@RequestBody Branch branch) {
        Branch savedBranch = branchService.addBranch(branch);
        return new ResponseEntity<>(savedBranch, HttpStatus.CREATED);
    }

    @PostMapping("/addBranchList")
    public ResponseEntity<List<Branch>> addBranch(@RequestBody List<Branch> branchList) {
        List<Branch> res = branchService.addBranchList(branchList);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    // Update an existing branch by branchId
    @PutMapping("/updateBranch/{branchId}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long branchId, @RequestBody Branch branchDetails) {
        Branch updatedBranch = branchService.updateBranch(branchId, branchDetails);
        return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
    }

    // Delete a branch by branchId
    @DeleteMapping("/deleteBranch/{branchId}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long branchId) {
        branchService.deleteBranch(branchId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllBranches")
    public ResponseEntity<Void> deleteAllBranches(){
        branchService.deleteAllBranches();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}