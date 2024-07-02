package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.exceptions.BranchNotFoundException;
import com.guidebook.GuideBook.exceptions.CollegeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(value = CollegeNotFoundException.class)
    public ResponseEntity<String> takeAction(CollegeNotFoundException collegeNotFoundException){
        log.error("CollegeNotFoundException occurred : {}" , collegeNotFoundException);
        return new ResponseEntity<>(collegeNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BranchNotFoundException.class)
    public ResponseEntity<String> takeAction(BranchNotFoundException branchNotFoundException){
        log.error("BranchNotFoundException occurred : {}" , branchNotFoundException);
        return new ResponseEntity<>(branchNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);

    }
}
