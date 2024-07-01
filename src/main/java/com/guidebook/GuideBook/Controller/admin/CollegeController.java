package com.guidebook.GuideBook.Controller.admin;

import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Services.admin.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/" )
@RestController
@RequestMapping("/api/v1/admin/")
public class CollegeController {
    private CollegeService collegeService;
    @Autowired
    CollegeController(CollegeService collegeService){
        this.collegeService = collegeService;
    }

    // Endpoint to add a new college
    @PostMapping("/addCollege")
    public ResponseEntity<College> addCollege(@RequestBody College college) {
        College savedCollege = collegeService.save(college);
        return new ResponseEntity<>(savedCollege, HttpStatus.CREATED);
    }

    // Endpoint to update a college
    @PutMapping("/updateCollege/{collegeId}")
    public ResponseEntity<College> updateCollege(@PathVariable Long collegeId, @RequestBody College collegeDetails) {
        College updatedCollege = collegeService.update(collegeId, collegeDetails);
        return new ResponseEntity<>(updatedCollege, HttpStatus.OK);
    }

    // Endpoint to delete a college
    @DeleteMapping("/deleteCollege/{collegeId}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long collegeId) {
        collegeService.delete(collegeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to retrieve all colleges
    @GetMapping("/colleges")
    public ResponseEntity<List<College>> getAllColleges() {
        List<College> colleges = collegeService.getAllColleges();
        return new ResponseEntity<>(colleges, HttpStatus.OK);
    }

    // Endpoint to retrieve a specific college by ID
    @GetMapping("/colleges/{collegeId}")
    public ResponseEntity<College> getCollegeById(@PathVariable Long collegeId) {
        College college = collegeService.getCollegeById(collegeId);
        return new ResponseEntity<>(college,HttpStatus.OK);

    }
}
