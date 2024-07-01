package com.guidebook.GuideBook.Services.admin;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Repository.CollegeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollegeService {
    private CollegeRepository collegeRepository;
    @Autowired
    CollegeService(CollegeRepository collegeRepository){
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

        if(collegeDetails.getCollegeName() != null){
            existingCollege.setCollegeName(collegeDetails.getCollegeName());
        }
        if(collegeDetails.getCollegeBranchList()!= null){
            existingCollege.setCollegeBranchList(collegeDetails.getCollegeBranchList());
        }
        if(collegeDetails.getCollegeStudentList() != null){
            existingCollege.setCollegeStudentList(collegeDetails.getCollegeStudentList());
        }
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

//    public void addBranchIdToCollegeId(Long collegeId, Long branchId){ //Internal method
//        Branch branch = branchService.getBranchById(branchId);
//        College college = collegeRepository.findById(collegeId).orElseThrow(
//                () -> new EntityNotFoundException("College not found with id: " + collegeId)
//        );
//        college.getCollegeBranchList().add(branch);
//    }

//    public void addStudentIdToCollegeId(Long collegeId, Long studentId){
//    }
}
