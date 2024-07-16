package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.EntranceExam;
import com.guidebook.GuideBook.Services.EntranceExamService;
import com.guidebook.GuideBook.dtos.AddEntranceExamRequest;
import com.guidebook.GuideBook.dtos.GetAllEntranceExamResponse;
import com.guidebook.GuideBook.dtos.GetCollegeListForExamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://guidebookx.s3-website.ap-south-1.amazonaws.com")
//@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class EntranceExamController {
    private EntranceExamService entranceExamService;
    @Autowired
    public EntranceExamController(EntranceExamService entranceExamService) {
        this.entranceExamService = entranceExamService;
    }

    @PostMapping("/addEntranceExam")
    public ResponseEntity<EntranceExam> addEntranceExam(@RequestBody AddEntranceExamRequest addEntranceExamRequest){
        EntranceExam exam = entranceExamService.addEntranceExam(addEntranceExamRequest);
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }

    @GetMapping("/entranceExams")
    public ResponseEntity<GetAllEntranceExamResponse> getAllEntranceExams(){
        GetAllEntranceExamResponse res = entranceExamService.getAllEntranceExams();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
