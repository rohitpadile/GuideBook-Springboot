package com.guidebook.GuideBook.Services.admin;

import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Repository.CollegeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCollegeService {
    private CollegeRepository collegeRepository;
    @Autowired
    AdminCollegeService(CollegeRepository collegeRepository){
        this.collegeRepository = collegeRepository;
    }

    // Method to save a new college
    public College save(College college) {
        return collegeRepository.save(college);
    }

    // Method to update an existing college
    public College update(Long collegeId, College collegeDetails) {
        College existingCollege = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new EntityNotFoundException("College not found with id: " + collegeId));

        existingCollege.setCollegeName(collegeDetails.getCollegeName());
//        existingCollege.setCollegeBranchIds(collegeDetails.getCollegeBranchIds());

        return collegeRepository.save(existingCollege);
    }

    // Method to delete a college by ID
    public void delete(Long collegeId) {
        College existingCollege = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new EntityNotFoundException("College not found with id: " + collegeId));

        collegeRepository.delete(existingCollege);
    }

    // Method to retrieve all colleges
    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }

    // Method to retrieve a specific college by ID
    public College getCollegeById(Long collegeId) {
        return collegeRepository.findById(collegeId)
                .orElseThrow(() -> new EntityNotFoundException("College not found with id: " + collegeId));
    }
}
