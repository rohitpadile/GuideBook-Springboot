package com.guidebook.GuideBook.TR.Controller;

import com.guidebook.GuideBook.TR.Services.TRUserService;
import com.guidebook.GuideBook.TR.dtos.AddTRUserRequest;
import com.guidebook.GuideBook.TR.dtos.DeleteTRUserRequest;
import com.guidebook.GuideBook.TR.dtos.UpdateTRUserRequest;
import com.guidebook.GuideBook.TR.exceptions.TRAdminPasswordException;
import com.guidebook.GuideBook.TR.exceptions.TRUserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/teamRecruiter/")
public class TRUserController {
    private TRUserService trUserService;
    @Autowired
    public TRUserController(TRUserService trUserService) {
        this.trUserService = trUserService;
    }

    @PostMapping("/addTRUser")
    public ResponseEntity<Void> addTRUser(AddTRUserRequest addTRUserRequest)
            throws TRAdminPasswordException {
        trUserService.addTRUser(addTRUserRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/updateTRUserPassword")
    public ResponseEntity<Void> updateTRUserPassword(UpdateTRUserRequest updateTRUserRequest)
            throws TRUserNotFoundException, TRAdminPasswordException {
        trUserService.updateTRUserPassword(updateTRUserRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/deleteTRUser")
    public ResponseEntity<Void> deleteTRUser(DeleteTRUserRequest deleteTRUserRequest)
            throws TRUserNotFoundException, TRAdminPasswordException {
        trUserService.deleteTRUser(deleteTRUserRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/TRUserList")
    public ResponseEntity<List<String>> getTRUserList(){
        List<String> res = trUserService.getTRUserList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
