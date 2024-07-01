package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Repository.BranchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {


    //Which Service to autowire to which service comes only when you learn to use DATA TRANSFER OBJECT

    private BranchRepository branchRepository;
    @Autowired
    private StudentService studentService;

    @Autowired
    BranchService(BranchRepository branchRepository){
        this.branchRepository = branchRepository;
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Branch addBranch(Branch branch){
        return branchRepository.save(branch);
    }

    public List<Branch> addBranchList(List<Branch> branchList){
        return branchRepository.saveAll(branchList);
    }

    public Branch updateBranch(Long branchId, Branch branchDetails) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));

        if(branchDetails.getBranchName() != null){
            branch.setBranchName(branchDetails.getBranchName());
        }
        if(branchDetails.getBranchStudentList() != null){
            branch.setBranchStudentList(branchDetails.getBranchStudentList());
        }
        return branchRepository.save(branch);
    }

    public Branch getBranchById(Long branchId){
        return branchRepository.findById(branchId).orElseThrow(
                () -> new EntityNotFoundException("Branch not found with id: " + branchId)
        );
    }

    public void deleteBranch(Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));
        branchRepository.delete(branch);
    }

    public void deleteAllBranches() {
        branchRepository.deleteAll();
    }

//    public Branch addStudentToBranch(Long branchid, Long studentId) {
//    }
}