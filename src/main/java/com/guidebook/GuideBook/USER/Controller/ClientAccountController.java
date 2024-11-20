package com.guidebook.GuideBook.USER.Controller;

import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Service.ClientAccountService;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import com.guidebook.GuideBook.USER.dtos.ClientAccountDetailsForZoomSessionFormResponse;
import com.guidebook.GuideBook.USER.dtos.EditMentorAccountRequest;
import com.guidebook.GuideBook.USER.exceptions.ClientAccountNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com", "https://a.guidebookx.com",
        "https://13.235.131.222",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/user/")
public class ClientAccountController {
    private final ClientAccountService clientAccountService;
    private final JwtUtil jwtUtil;
    @Autowired
    public ClientAccountController(ClientAccountService clientAccountService,
                                   JwtUtil jwtUtil) {
        this.clientAccountService = clientAccountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/editClientAccountDetails")
    public ResponseEntity<Void> editClientAccountDetails(
            @RequestBody EditMentorAccountRequest editMentorAccountRequest, HttpServletRequest request
            ) throws ClientAccountNotFoundException {
        String clientEmail = jwtUtil.extractEmailFromToken(request);
        clientAccountService.editClientAccountDetails(editMentorAccountRequest, clientEmail);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/getClientAccountDetailsForZoomSessionForm")
    public ResponseEntity<ClientAccountDetailsForZoomSessionFormResponse> getClientAccountDetailsForZoomSessionForm(HttpServletRequest request)
            throws ClientAccountNotFoundException {
        String clientEmail = jwtUtil.extractEmailFromToken(request);
        ClientAccountDetailsForZoomSessionFormResponse res = clientAccountService.getClientAccountDetailsForZoomSessionForm(clientEmail);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/allClientAccounts") //for personal use
    public ResponseEntity<List<ClientAccount>> getAllClientAccounts(){
        return new ResponseEntity<>(clientAccountService.getAllClientAccounts(), HttpStatus.OK);
    }
}
