package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Models.Branch;
import com.guidebook.GuideBook.ADMIN.dtos.AddCollegeRequest;
import com.guidebook.GuideBook.ADMIN.dtos.GetCollegeListForExamResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.CollegeNotFoundException;
import com.guidebook.GuideBook.ADMIN.exceptions.EntranceExamNotFoundException;
import com.guidebook.GuideBook.ADMIN.mapper.CollegeMapper;
import com.guidebook.GuideBook.ADMIN.Models.College;
import com.guidebook.GuideBook.ADMIN.Models.EntranceExam;
import com.guidebook.GuideBook.ADMIN.Repository.CollegeRepository;
import com.guidebook.GuideBook.ADMIN.Repository.cutomrepository.CustomCollegeRepositoryImpl;
//import com.guidebook.GuideBook.dtos.GetAllCollegeListForClubsResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public GetCollegeListForExamResponse getCollegeListForExamRequest(String examName) {
        GetCollegeListForExamResponse getCollegeListForExamResponse = new GetCollegeListForExamResponse();

        //IF SOMEONE CHANGED THE STATIC CONTENT OF THE PAGE AND GAVE RANDOM NAME TO EXAM-NAME, STILL WE
        //HAVE TO SHOW ALL THE COLLEGES
        //CODE FOR THAT CASE
        List<College> colleges = customCollegeRepositoryImpl.findCollegeByEntranceExamNameIgnoreCase(examName);
        if(colleges == null || colleges.isEmpty() || examName.equalsIgnoreCase("all")){
            //WRONG EXAM NAME ENTERED! - SOMEONE CHEATING ON THE PAGE
            //RETURN ALL THE COLLEGES IN THE LIST
            colleges = collegeRepository.findAll();
            for(College clg : colleges){
                getCollegeListForExamResponse.getCollegeNameList().add(clg.getCollegeName());
            }
        } else {
            for(College clg : colleges){
                getCollegeListForExamResponse.getCollegeNameList().add(clg.getCollegeName());
            }
        }
        return getCollegeListForExamResponse;
    }
    @Transactional
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
                        " Rolling back the whole method");
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

    public College getCollegeByCollegeNameIgnoreCase(String name)
    throws CollegeNotFoundException {
        return collegeRepository.findCollegeByCollegeNameIgnoreCase(name);
    }

    public List<String> getAllCollegeNameList(){
        List<String> result = new ArrayList<>();
        List<College> colleges = collegeRepository.findAll();
        for(College clg : colleges){
            result.add(clg.getCollegeName());
        }
        return result;
    }

//    public GetAllCollegeListForClubsResponse getCollegesForClubs() {
//        GetAllCollegeListForClubsResponse response = new GetAllCollegeListForClubsResponse();
//        for(College college : collegeRepository.findAll()){
//            response.getCollegeNameList().add(college.getCollegeName());
//        }
//        return response;
//    }
}
