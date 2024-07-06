package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.EntranceExam;
import com.guidebook.GuideBook.Repository.EntranceExamRepository;
import com.guidebook.GuideBook.dtos.AddEntranceExamRequest;
import com.guidebook.GuideBook.dtos.GetAllEntranceExamResponse;
import com.guidebook.GuideBook.dtos.GetCollegeListForExamResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EntranceExamService {
    private EntranceExamRepository entranceExamRepository;
    @Autowired
    public EntranceExamService(EntranceExamRepository entranceExamRepository) {
        this.entranceExamRepository = entranceExamRepository;
    }

    public EntranceExam getEntranceExamByNameIgnoreCase(String examName){
        return entranceExamRepository.findByEntranceExamNameIgnoreCase(examName);
    }

    public EntranceExam addEntranceExam(AddEntranceExamRequest addEntranceExamRequest) {
        EntranceExam newExam = new EntranceExam();
        if(addEntranceExamRequest.getExamName()!=null){
            newExam.setEntranceExamName(addEntranceExamRequest.getExamName());
        }
        return entranceExamRepository.save(newExam);

    }

    public GetAllEntranceExamResponse getAllEntranceExams() {
        List<EntranceExam> examList = entranceExamRepository.findAll();
        GetAllEntranceExamResponse response = new GetAllEntranceExamResponse();
        for(EntranceExam exam : examList){
            response.getEntranceExamNameSet().add(exam.getEntranceExamName());
        }
        return response;
    }
}
