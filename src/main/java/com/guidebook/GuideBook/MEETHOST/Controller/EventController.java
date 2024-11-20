package com.guidebook.GuideBook.MEETHOST.Controller;

import com.guidebook.GuideBook.MEETHOST.Model.Ticket;
import com.guidebook.GuideBook.MEETHOST.Service.EventService;
import com.guidebook.GuideBook.MEETHOST.dtos.*;
import com.guidebook.GuideBook.MEETHOST.exceptions.EventNotFoundException;
import com.guidebook.GuideBook.USER.Models.MyUser;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import com.guidebook.GuideBook.USER.Service.MyUserService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/v1/meethost/")
public class EventController {
    private final EventService eventService;
    private final JwtUtil jwtUtil;
    private final MyUserService myUserService;
    @Autowired
    public EventController(EventService eventService,
                           JwtUtil jwtUtil,
                           MyUserService myUserService) {
        this.eventService = eventService;
        this.jwtUtil = jwtUtil;
        this.myUserService = myUserService;
    }

    @PostMapping("/addNewEvent")
    public ResponseEntity<String> addNewEvent(@RequestBody AddNewEventRequest request){
        eventService.addNewEvent(request);
        return new ResponseEntity<>("Event added sucessfully",HttpStatus.CREATED);
    }

    @PostMapping("/updateEvent")
    public ResponseEntity<Void> updateEvent(@RequestBody UpdateEventRequest request)
            throws EventNotFoundException {
        eventService.updateEvent(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getHomePageEventList") //take this via pageable
    public ResponseEntity<ActiveEventListResponse> getHomePageEventList()
            throws EventNotFoundException {
        //Return the events in Page wise which are boolean isOnline = true;
        ActiveEventListResponse res = eventService.getHomePageEventList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/activateEventsWithCode")
    public ResponseEntity<String> activateEventsWithCode(@RequestBody ActivateEventsWithCodeRequest request)
            throws EventNotFoundException {
        eventService.activateEventsWithCode(request);
        return new ResponseEntity<>("Events Activated Successfully", HttpStatus.OK);
    }

    @GetMapping("/getEventDetails/{eventCode}")
    public ResponseEntity<GetEventDetailsResponse> getEventDetails(@PathVariable String eventCode)
            throws EventNotFoundException {
        GetEventDetailsResponse res = eventService.getEventDetails(eventCode);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/checkIfEventBookedByUser/{eventCode}")
    public ResponseEntity<checkEventBookedResponse> checkIfEventBookedByUser(
            @PathVariable String eventCode,
            HttpServletRequest request
    ){
        int isBooked = 0;
        String userEmail = jwtUtil.extractEmailFromToken(request);
        MyUser user = myUserService.getMyUserRepository().findByUsername(userEmail);
        for(Ticket ticket :  user.getTicketList()){
            if(ticket.getEventCode().equals(eventCode)){
                isBooked = 1; //disable event book button in frontend
            }
        }
        return new ResponseEntity<>(
                checkEventBookedResponse.builder().isBooked(isBooked).build(),
                HttpStatus.OK);
    }
}
