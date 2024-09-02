package com.guidebook.GuideBook.MEETHOST.Controller;

import com.guidebook.GuideBook.MEETHOST.Service.EventService;
import com.guidebook.GuideBook.MEETHOST.Service.TicketService;
import com.guidebook.GuideBook.MEETHOST.dtos.AddNewEventRequest;
import com.guidebook.GuideBook.MEETHOST.dtos.UpdateEventRequest;
import com.guidebook.GuideBook.MEETHOST.exceptions.EventNotFoundException;
import jakarta.validation.Valid;
import okhttp3.Response;
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
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
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


}
