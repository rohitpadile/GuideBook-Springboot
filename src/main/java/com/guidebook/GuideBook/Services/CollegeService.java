package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Models.EntranceExam;
import com.guidebook.GuideBook.Repository.CollegeRepository;
import com.guidebook.GuideBook.Repository.cutomrepository.CustomCollegeRepositoryImpl;
import com.guidebook.GuideBook.dtos.AddCollegeRequest;
import com.guidebook.GuideBook.dtos.GetCollegeListForExamResponse;
import com.guidebook.GuideBook.exceptions.EntranceExamNotFoundException;
import com.guidebook.GuideBook.mapper.CollegeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CollegeService {

    private EntranceExamService entranceExamService;
    private BranchService branchService;
    private CollegeRepository collegeRepository;
    private CustomCollegeRepositoryImpl customCollegeRepositoryImpl;
    @Autowired
    CollegeService(CollegeRepository collegeRepository,
                   EntranceExamService entranceExamService,
                   BranchService branchService,
                   CustomCollegeRepositoryImpl customCollegeRepositoryImpl
    ){
        this.collegeRepository = collegeRepository;
        this.entranceExamService = entranceExamService;
        this.branchService = branchService;
        this.customCollegeRepositoryImpl = customCollegeRepositoryImpl;
    }

    // Method to save a new college
    public College save(College college) {
        return collegeRepository.save(college);
    }

    // Method to retrieve all colleges
    public GetCollegeListForExamResponse getCollegeListForExam(String examName) {
        List<College> colleges = customCollegeRepositoryImpl.findCollegeByEntranceExamNameIgnoreCase(examName);
        GetCollegeListForExamResponse getCollegeListForExamResponse = new GetCollegeListForExamResponse();
        for(College clg : colleges){
            getCollegeListForExamResponse.getCollegeNameList().add(clg.getCollegeName());
        }
        return getCollegeListForExamResponse;
    }
    public void addCollegeWithBranches(AddCollegeRequest addCollegeRequest) throws EntranceExamNotFoundException {
        College newCollege = CollegeMapper.mapToCollege(addCollegeRequest);

        Set<Branch> branchSet = new HashSet<>(); //This set is to be added to branch table
        for(String branchName : addCollegeRequest.getBranchNames()){
            if((branchService.getBranchByBranchNameIgnoreCase(branchName)) == null){
                Branch branch = new Branch(); //make a new branch , add it to collegeBranchSet
                branch.setBranchName(branchName);
                Branch newBranchAdded = branchService.addBranch(branch); //adding the new branch to branch entity
                branchSet.add(newBranchAdded);//adding it to collegeBranchSet
            } else {//already branch present
                log.info("Branch {} is already in the branch table", branchName);
                branchSet.add(branchService.getBranchByBranchNameIgnoreCase(branchName)); //add it to collegeBranchSet
            }
        }
        newCollege.setCollegeBranchSet(branchSet);

        Set<EntranceExam> entranceExams = new HashSet<>();
        for(String examName : addCollegeRequest.getCollegeEntranceExamNameSet()){
            if((entranceExamService.getEntranceExamByNameIgnoreCase(examName))==null){
                throw new EntranceExamNotFoundException("Entrance exam not found: " + examName +"." +
                        " Exams which exists might be added to the college Entrance Set");
            } else {
                //Exam found
                //Add it to the collegeEntrance Set
                EntranceExam exam = entranceExamService.getEntranceExamByNameIgnoreCase(examName);
                entranceExams.add(exam);
            }
        }
        newCollege.setCollegeEntranceSet(entranceExams);
        collegeRepository.save(newCollege);
    }

    public College getCollegeByCollegeNameIgnoreCase(String name){
        return collegeRepository.findCollegeByCollegeNameIgnoreCase(name);
    }
}
