package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.Models.EntranceExam;
import com.guidebook.GuideBook.ADMIN.Services.EntranceExamService;
import com.guidebook.GuideBook.ADMIN.dtos.AddEntranceExamRequest;
import com.guidebook.GuideBook.ADMIN.dtos.GetAllEntranceExamResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.AlreadyPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com", "https://a.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/admin/")
public class EntranceExamController {
    private EntranceExamService entranceExamService;
    @Autowired
    public EntranceExamController(EntranceExamService entranceExamService) {
        this.entranceExamService = entranceExamService;
    }

    @PostMapping("/addEntranceExam")
    public ResponseEntity<EntranceExam> addEntranceExam(@RequestBody AddEntranceExamRequest addEntranceExamRequest)
            throws AlreadyPresentException {
        EntranceExam exam = entranceExamService.addEntranceExam(addEntranceExamRequest);
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }

    @GetMapping("/entranceExams")
    public ResponseEntity<GetAllEntranceExamResponse> getAllEntranceExams(){
        GetAllEntranceExamResponse res = entranceExamService.getAllEntranceExams();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
