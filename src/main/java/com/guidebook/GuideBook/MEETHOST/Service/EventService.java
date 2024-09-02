package com.guidebook.GuideBook.MEETHOST.Service;

import com.guidebook.GuideBook.MEETHOST.Model.Event;
import com.guidebook.GuideBook.MEETHOST.Repository.EventRepository;
import com.guidebook.GuideBook.MEETHOST.dtos.AddNewEventRequest;
import com.guidebook.GuideBook.MEETHOST.dtos.UpdateEventRequest;
import com.guidebook.GuideBook.MEETHOST.exceptions.EventNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final TicketService ticketService;
    @Autowired
    public EventService(EventRepository eventRepository,
                        TicketService ticketService) {
        this.eventRepository = eventRepository;
        this.ticketService = ticketService;
    }

    @Transactional
    public void addNewEvent(AddNewEventRequest request) {
        Event event = new Event();
        event.setEventName(request.getEventName());
        event.setEventDescription(request.getEventDescription());
        event.setEventLocation(request.getEventLocation());
        event.setEventType(request.getEventType());
        event.setZoomLink(request.getZoomLink());
        event.setRegistrationLink(request.getRegistrationLink());
        event.setDateAndTime(request.getDateAndTime());

        //Create a new Ticket for this event
        ticketService.createNewTicket(request);
        eventRepository.save(event);
    }
    @Transactional
    public void updateEvent(UpdateEventRequest request)
        throws EventNotFoundException {
        Optional<Event> checkEvent = eventRepository.findById(request.getEventId());
        if(checkEvent.isPresent()){
            Event myEvent = updateEventDetails(request, checkEvent);
            eventRepository.save(myEvent);
        } else {
            throw new EventNotFoundException("Event not found at updateEvent Method. Event-Name: " + request.getEventName() + " Event-Id: " + request.getEventId());
        }
    }

    @NotNull
    @Transactional
    private static Event updateEventDetails(UpdateEventRequest request, Optional<Event> checkEvent) {
        Event myEvent = checkEvent.get();
        //update event details now
        myEvent.setEventName(request.getEventName());
        myEvent.setEventDescription(request.getEventDescription());
        myEvent.setEventLocation(request.getEventLocation());
        myEvent.setRegistrationLink(request.getRegistrationLink());
        myEvent.setZoomLink(request.getZoomLink());
        myEvent.setOrganizer(request.getOrganizer());
        myEvent.setDateAndTime(request.getDateAndTime());
        return myEvent;
    }
}
