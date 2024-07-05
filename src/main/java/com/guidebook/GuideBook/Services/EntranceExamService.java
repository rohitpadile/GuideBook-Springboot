package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.EntranceExam;
import com.guidebook.GuideBook.Repository.EntranceExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
