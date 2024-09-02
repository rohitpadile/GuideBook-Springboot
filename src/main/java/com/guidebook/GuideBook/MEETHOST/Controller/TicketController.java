package com.guidebook.GuideBook.MEETHOST.Controller;

import com.guidebook.GuideBook.MEETHOST.Service.TicketService;
import com.guidebook.GuideBook.MEETHOST.dtos.TicketResponse;
import com.guidebook.GuideBook.USER.Models.MyUser;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import com.guidebook.GuideBook.USER.Service.MyUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/meethost/")
public class TicketController {
    private final TicketService ticketService;
    private final JwtUtil jwtUtil;
    private final MyUserService myUserService;
    @Autowired
    public TicketController(TicketService ticketService,
                            JwtUtil jwtUtil,
                            MyUserService myUserService) {
        this.ticketService = ticketService;
        this.jwtUtil = jwtUtil;
        this.myUserService = myUserService;
    }

    @GetMapping("/getUserBookedTickets")
    public ResponseEntity<List<TicketResponse>> getUserBookedTickets(
            HttpServletRequest request
    ){
        String userEmail = jwtUtil.extractEmailFromToken(request);
        MyUser user = myUserService.getMyUserRepository().findByUsername(userEmail);
        List<TicketResponse> res =  ticketService.getBookedTicketsForUser(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
