package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Repository.BranchRepository;
import com.guidebook.GuideBook.ADMIN.Repository.cutomrepository.CustomBranchRepositoryImpl;
import com.guidebook.GuideBook.ADMIN.dtos.AddBranchRequest;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeRequest;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.BranchNotFoundException;
import com.guidebook.GuideBook.ADMIN.mapper.BranchMapper;
import com.guidebook.GuideBook.ADMIN.Models.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchService {


    //Which Service to autowire to which service comes only when you learn to use DATA TRANSFER OBJECT

    private BranchRepository branchRepository;
    private CustomBranchRepositoryImpl customBranchRepositoryImpl;

    @Autowired
    BranchService(BranchRepository branchRepository, CustomBranchRepositoryImpl customBranchRepositoryImpl){
        this.branchRepository = branchRepository;
        this.customBranchRepositoryImpl = customBranchRepositoryImpl;
    }
    public Branch getBranchById(Long branchId) throws BranchNotFoundException {
        return branchRepository.findById(branchId).orElseThrow(
                () -> new BranchNotFoundException("Branch not found with id: " + branchId)
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
    public List<String> getAllBranchNameList(){
        List<String> result = new ArrayList<>();
        List<Branch> branches = branchRepository.findAll();
        for(Branch branch : branches){
            result.add(branch.getBranchName());
        }
        return result;
    }

    public GetAllBranchNameListForCollegeResponse getAllBranchNameListForCollege(GetAllBranchNameListForCollegeRequest request)
    throws BranchNotFoundException{
        List<Branch> branches = customBranchRepositoryImpl.findBranchesForCollegeIgnoreCase(request);
        GetAllBranchNameListForCollegeResponse response = new GetAllBranchNameListForCollegeResponse();

        for (Branch branch : branches) {
            response.getAllBranchNamesForCollegeList().add(branch.getBranchName());
        }
        return response;
    }

//    public Branch addStudentToBranch(Long branchid, Long studentId) {
//    }
}