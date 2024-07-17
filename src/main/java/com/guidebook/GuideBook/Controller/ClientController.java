package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Client;
import com.guidebook.GuideBook.Services.ClientService;
import com.guidebook.GuideBook.dtos.accountDtos.SignupRequest;
import com.guidebook.GuideBook.dtos.accountDtos.SignupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://guidebookx.s3-website.ap-south-1.amazonaws.com")
@RestController
@RequestMapping("/api/v1/admin/")
public class ClientController {
    private ClientService clientService;
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> addAccount(@RequestBody SignupRequest signupRequest){
        SignupResponse res = clientService.addAccount(signupRequest);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    //TO CHECK WHO IS THE CURRENT LOGGED IN PROFILE
}
