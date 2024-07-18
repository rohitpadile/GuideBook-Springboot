package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.account.UtilityClass;
import com.guidebook.GuideBook.dtos.GetCurrentLoggedInProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://guidebookx.s3-website.ap-south-1.amazonaws.com", "http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class AccountController {

    @GetMapping("/currentLoggedInProfileEmail")
    public ResponseEntity<GetCurrentLoggedInProfileResponse> getCurrentLoggedInProfile(){
        GetCurrentLoggedInProfileResponse res = new GetCurrentLoggedInProfileResponse();
        res.setCurrentLoggedInProfileEmail(
                UtilityClass.currentLoggedInProfileEmail
        );
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
