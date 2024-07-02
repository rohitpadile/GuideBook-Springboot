package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Repository.BranchRepository;
import com.guidebook.GuideBook.dtos.AddBranchRequest;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllBranchNameListResponse;
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
    BranchService(BranchRepository branchRepository){
        this.branchRepository = branchRepository;
    }
    public Branch getBranchById(Long branchId){
        return branchRepository.findById(branchId).orElseThrow(
                () -> new EntityNotFoundException("Branch not found with id: " + branchId)
        );
    }

    public Branch addBranch(AddBranchRequest addBranchRequest){
        Branch newbranchAdded = BranchMapper.mapToBranch(addBranchRequest);
        return branchRepository.save(newbranchAdded);

    }

    public Branch addBranch(Branch branch){
        return branchRepository.save(branch);
    }

    public Branch getBranchByBranchNameIgnoreCase(String name){
        return branchRepository.findBranchByBranchNameIgnoreCase(name);
    }

    public List<Branch> getAllbranches() {
        return branchRepository.findAll();
    }

    public GetAllBranchNameListResponse getAllbranchNamesList() {
        GetAllBranchNameListResponse response = new GetAllBranchNameListResponse();
        List<Branch> branches = branchRepository.findAll();

        for(Branch branch : branches){
            response.getAllBranchNamesList().add(branch.getBranchName());
        }
        return response;
    }

//    public Branch addStudentToBranch(Long branchid, Long studentId) {
//    }
}