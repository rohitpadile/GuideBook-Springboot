package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Models.EntranceExam;
import com.guidebook.GuideBook.ADMIN.Repository.EntranceExamRepository;
import com.guidebook.GuideBook.ADMIN.dtos.AddEntranceExamRequest;
import com.guidebook.GuideBook.ADMIN.dtos.GetAllEntranceExamResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.AlreadyPresentException;
import jakarta.transaction.Transactional;
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
    @Transactional
    public EntranceExam addEntranceExam(AddEntranceExamRequest addEntranceExamRequest)
            throws AlreadyPresentException {
        if((entranceExamRepository.findByEntranceExamNameIgnoreCase(addEntranceExamRequest.getExamName())) != null){
            throw new AlreadyPresentException("Entrance exam already present at addEntranceExam() method");
        }
        EntranceExam newExam = new EntranceExam();
        if(addEntranceExamRequest.getExamName()!=null){
            newExam.setEntranceExamName(addEntranceExamRequest.getExamName());
        }
        return entranceExamRepository.save(newExam);

    }

    public GetAllEntranceExamResponse getAllEntranceExams() {
        //Increase number of people visited count by 1


        List<EntranceExam> examList = entranceExamRepository.findAll();
        GetAllEntranceExamResponse response = new GetAllEntranceExamResponse();
        for(EntranceExam exam : examList){
            response.getEntranceExamNameSet().add(exam.getEntranceExamName());
        }
        return response;
    }
}
