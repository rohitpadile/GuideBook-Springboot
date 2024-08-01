package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.exceptions.*;
import com.guidebook.GuideBook.exceptions.*;
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

    @ExceptionHandler(value = StudentBasicDetailsNotFoundException.class)
    public ResponseEntity<String> takeAction(StudentBasicDetailsNotFoundException studentBasicDetailsNotFoundException){
        log.error("StudentBasicDetailsNotFoundException occurred : {}" , studentBasicDetailsNotFoundException);
        return new ResponseEntity<>(studentBasicDetailsNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = StudentCategoryNotFoundException.class)
    public ResponseEntity<String> takeAction(StudentCategoryNotFoundException studentCategoryNotFoundException){
        log.error("StudentCategoryNotFoundException occurred : {}" , studentCategoryNotFoundException);
        return new ResponseEntity<>(studentCategoryNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = StudentClassTypeNotFoundException.class)
    public ResponseEntity<String> takeAction(StudentClassTypeNotFoundException studentClassTypeNotFoundException){
        log.error("StudentClassTypeNotFoundException occurred : {}" , studentClassTypeNotFoundException);
        return new ResponseEntity<>(studentClassTypeNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = StudentProfileContentNotFoundException.class)
    public ResponseEntity<String> takeAction(StudentProfileContentNotFoundException studentProfileContentNotFoundException){
        log.error("StudentProfileContentNotFoundException occurred : {}" , studentProfileContentNotFoundException);
        return new ResponseEntity<>(studentProfileContentNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = EntranceExamNotFoundException.class)
    public ResponseEntity<String> takeAction(EntranceExamNotFoundException entranceExamNotFoundException){
        log.error("EntranceExamNotFoundException occurred : {}" , entranceExamNotFoundException);
        return new ResponseEntity<>(entranceExamNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = CollegeClubNotFoundException.class)
    public ResponseEntity<String> takeAction(CollegeClubNotFoundException collegeClubNotFoundException){
        log.error("CollegeClubNotFoundException occurred : {}" , collegeClubNotFoundException);
        return new ResponseEntity<>(collegeClubNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EncryptionFailedException.class)
    public ResponseEntity<String> takeAction(EncryptionFailedException encryptionFailedException){
        log.error("EncryptionFailedException occurred : {}" , encryptionFailedException);
        return new ResponseEntity<>(encryptionFailedException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ZoomSessionNotFoundException.class)
    public ResponseEntity<String> takeAction(ZoomSessionNotFoundException zoomSessionNotFoundException){
        log.error("ZoomSessionNotFoundException occurred : {}" , zoomSessionNotFoundException);
        return new ResponseEntity<>(zoomSessionNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TransactionNotFoundException.class)
    public ResponseEntity<String> takeAction(TransactionNotFoundException transactionNotFoundException){
        log.error("TransactionNotFoundException occurred : {}" , transactionNotFoundException);
        return new ResponseEntity<>(transactionNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = LanguageNotFoundException.class)
    public ResponseEntity<String> takeAction(LanguageNotFoundException languageNotFoundException){
        log.error("LanguageNotFoundException occurred : {}" , languageNotFoundException);
        return new ResponseEntity<>(languageNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FilteredStudentListNotFoundException.class)
    public ResponseEntity<String> takeAction(FilteredStudentListNotFoundException filteredStudentListNotFoundException){
        log.error("FilteredStudentListNotFoundException occurred : {}" , filteredStudentListNotFoundException);
        return new ResponseEntity<>(filteredStudentListNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
