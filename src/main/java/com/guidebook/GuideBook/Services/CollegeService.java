package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Repository.CollegeRepository;
import com.guidebook.GuideBook.dtos.AddCollegeRequest;
import com.guidebook.GuideBook.dtos.GetCollegeListResponse;
import com.guidebook.GuideBook.mapper.CollegeMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CollegeService {
    @Autowired
    private BranchService branchService;
    private CollegeRepository collegeRepository;
    @Autowired
    CollegeService(CollegeRepository collegeRepository){
        this.collegeRepository = collegeRepository;
    }

    // Method to save a new college
    public College save(College college) {
        return collegeRepository.save(college);
    }

    // Method to retrieve all colleges
    public GetCollegeListResponse getCollegeListRequest() {
        List<College> colleges = collegeRepository.findAll();
        GetCollegeListResponse getCollegeListResponse = new GetCollegeListResponse();
        for(College clg : colleges){
            getCollegeListResponse.getCollegeNameList().add(clg.getCollegeName());
        }
        return getCollegeListResponse;
    }
    public void addCollegeWithBranches(AddCollegeRequest addCollegeRequest){
        College newCollege = CollegeMapper.mapToCollege(addCollegeRequest);

        Set<Branch> branchSet = new HashSet<>(); //This set is to be added to branch table
        for(String branchName : addCollegeRequest.getBranchNames()){
            if((branchService.getBranchByName(branchName)) == null){
                Branch branch = new Branch(); //make a new branch , add it to collegeBranchSet
                branch.setBranchName(branchName);
                Branch newBranchAdded = branchService.addBranch(branch); //adding the new branch to branch entity
                branchSet.add(newBranchAdded);//adding it to collegeBranchSet
            } else {//already branch present
                log.info("Branch {} is already in the branch table", branchName);
                branchSet.add(branchService.getBranchByName(branchName)); //add it to collegeBranchSet
            }
        }
        newCollege.setCollegeBranchSet(branchSet);
        collegeRepository.save(newCollege);

    }

    public College findCollegeByCollegeName(String name){
        return collegeRepository.findCollegeByCollegeName(name);
    }
}
