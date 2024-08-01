package com.guidebook.GuideBook.TR.Services;

import com.guidebook.GuideBook.ADMIN.Models.Branch;
import com.guidebook.GuideBook.ADMIN.Repository.BranchRepository;
import com.guidebook.GuideBook.TR.dtos.TRGetAllBranchNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TRBranchService {
    private BranchRepository branchRepository;
    @Autowired
    public TRBranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }



    public TRGetAllBranchNameResponse getAllBranchNames() {
        List<Branch> branches = branchRepository.findAll();
        TRGetAllBranchNameResponse response = new TRGetAllBranchNameResponse();
        for(Branch branch : branches){
            response.getAllBranches().add(branch.getBranchName());
        }
        return response;
    }
}
