package com.guidebook.GuideBook.MEETHOST.Controller;

import com.guidebook.GuideBook.MEETHOST.Service.EventService;
import com.guidebook.GuideBook.MEETHOST.Service.TicketService;
import com.guidebook.GuideBook.MEETHOST.dtos.AddNewEventRequest;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/meethost/")
public class EventController {
    private final EventService eventService;
    private final TicketService ticketService;
    @Autowired
    public EventController(EventService eventService, TicketService ticketService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Void> addNewEvent(@RequestBody AddNewEventRequest request){
        eventService.addNewEvent(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
