package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Services.AdminCollegeService;
import com.guidebook.GuideBook.Services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/" )
@RestController
@RequestMapping("/api/v1/admin/")
public class AdminCollegeController {

    @Autowired
    private AdminCollegeService adminCollegeService;

    // Endpoint to add a new college
    @PostMapping("/addCollege")
    public ResponseEntity<College> addCollege(@RequestBody College college) {
        College savedCollege = adminCollegeService.save(college);
        return ResponseEntity.ok(savedCollege);
    }

    // Endpoint to update a college
    @PutMapping("/updateCollege/{collegeId}")
    public ResponseEntity<College> updateCollege(@PathVariable Long collegeId, @RequestBody College collegeDetails) {
        College updatedCollege = adminCollegeService.update(collegeId, collegeDetails);
        return ResponseEntity.ok(updatedCollege);
    }

    // Endpoint to delete a college
    @DeleteMapping("/deleteCollege/{collegeId}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long collegeId) {
        adminCollegeService.delete(collegeId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to retrieve all colleges
    @GetMapping("/colleges")
    public ResponseEntity<List<College>> getAllColleges() {
        List<College> colleges = adminCollegeService.getAllColleges();
        return ResponseEntity.ok(colleges);
    }

    // Endpoint to retrieve a specific college by ID
    @GetMapping("/colleges/{collegeId}")
    public ResponseEntity<College> getCollegeById(@PathVariable Long collegeId) {
        College college = adminCollegeService.getCollegeById(collegeId);
        if (college != null) {
            return ResponseEntity.ok(college);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
