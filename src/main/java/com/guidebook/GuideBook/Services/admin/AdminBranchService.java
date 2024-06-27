package com.guidebook.GuideBook.Services.admin;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Repository.BranchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBranchService {

    private BranchRepository branchRepository;

    @Autowired
    AdminBranchService(BranchRepository branchRepository){
        this.branchRepository = branchRepository;
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Branch addBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch updateBranch(Long branchId, Branch branchDetails) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));

        branch.setBranchName(branchDetails.getBranchName());


        return branchRepository.save(branch);
    }

    public void deleteBranch(Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));

        branchRepository.delete(branch);
    }
}