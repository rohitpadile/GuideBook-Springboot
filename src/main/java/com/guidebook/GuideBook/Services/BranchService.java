package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Repository.BranchRepository;
import com.guidebook.GuideBook.dtos.AddBranchRequest;
import com.guidebook.GuideBook.mapper.BranchMapper;
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
    public Branch getBranchById(Long branchId){
        return branchRepository.findById(branchId).orElseThrow(
                () -> new EntityNotFoundException("Branch not found with id: " + branchId)
        );
    }

    public Branch addBranch(AddBranchRequest addBranchRequest){
        Branch newBranch = new Branch();
        newBranch.setBranchName(addBranchRequest.getBranchName());
        return addBranch(newBranch);
    }

    public Branch addBranch(Branch branch){
        return branchRepository.save(branch);
    }

    public Branch getBranchByName(String name){
        return branchRepository.findBranchByBranchName(name);
    }

//    public Branch addStudentToBranch(Long branchid, Long studentId) {
//    }
}