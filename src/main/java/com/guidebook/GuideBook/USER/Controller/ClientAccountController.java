package com.guidebook.GuideBook.USER.Controller;

import com.guidebook.GuideBook.USER.Service.ClientAccountService;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import com.guidebook.GuideBook.USER.dtos.EditClientAccountRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
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
            @RequestBody EditClientAccountRequest editClientAccountRequest, HttpServletRequest request
            ){
        String clientEmail = jwtUtil.extractEmailFromToken(request);
        clientAccountService.editClientAccountDetails(editClientAccountRequest, clientEmail);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
