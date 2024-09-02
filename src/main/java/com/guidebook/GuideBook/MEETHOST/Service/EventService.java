package com.guidebook.GuideBook.MEETHOST.Service;

import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionForm;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.MEETHOST.Model.Event;
import com.guidebook.GuideBook.MEETHOST.Repository.EventRepository;
import com.guidebook.GuideBook.MEETHOST.dtos.*;
import com.guidebook.GuideBook.MEETHOST.exceptions.EventNotFoundException;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Service.ClientAccountService;
import com.guidebook.GuideBook.USER.Service.MyUserService;
import com.guidebook.GuideBook.USER.exceptions.MyUserAccountNotExistsException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final TicketService ticketService;
    private final EmailServiceImpl emailServiceImpl;
    private final MyUserService myUserService;
    private final StudentService studentService;
    private final ClientAccountService clientAccountService;
    @Autowired
    public EventService(EventRepository eventRepository,
                        TicketService ticketService,
                        EmailServiceImpl emailServiceImpl,
                        MyUserService myUserService,
                        StudentService studentService,
                        ClientAccountService clientAccountService) {
        this.eventRepository = eventRepository;
        this.ticketService = ticketService;
        this.studentService= studentService;
        this.myUserService = myUserService;
        this.emailServiceImpl = emailServiceImpl;
        this.clientAccountService = clientAccountService;
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
        event.setEventCode(request.getEventCode());
        event.setOrganizer(request.getOrganizer());
        event.setIsActive(0); //initially not activated event
        //This event code is same across all references of this event
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
    @Transactional
    public ActiveEventListResponse getHomePageEventList() throws EventNotFoundException {
        ActiveEventListResponse response = new ActiveEventListResponse();

        // Pass Integer value instead of boolean
        List<Event> activeEvents = eventRepository.findByIsActive(1); // 1 denotes activated
        if (activeEvents.isEmpty()) {
            throw new EventNotFoundException("No active events found.");
        }
        for (Event event : activeEvents) {
            response.getActiveEventCodeList().add(event.getEventCode());
        }
        return response;
    }
    @Transactional
    public void activateEventsWithCode(ActivateEventsWithCodeRequest request)
            throws EventNotFoundException {
        for(String eventCode : request.getEventCodeList()){
            Optional<Event> checkEvent = eventRepository.findByEventCode(eventCode);
            if(checkEvent.isPresent()){
                checkEvent.get().setIsActive(1);
                eventRepository.save(checkEvent.get());
            } else {
                throw new EventNotFoundException("One of the Event in list not found at activateEventsWithCode() method");
            }
        }
    }

    public GetEventDetailsResponse getEventDetails(String eventCode)
            throws EventNotFoundException {
        Optional<Event> checkEvent = eventRepository.findByEventCode(eventCode);
        if (checkEvent.isPresent()) {
            Event event = checkEvent.get();
            return GetEventDetailsResponse.builder()
                    .eventName(event.getEventName())
                    .eventType(event.getEventType()) // Assuming it's a string or adjust based on the enum type
                    .eventDescription(event.getEventDescription())
                    .eventLocation(event.getEventLocation())
                    .registrationLink(event.getRegistrationLink())
                    .isActive(event.getIsActive())
                    .organizer(event.getOrganizer())
                    .zoomLink(event.getZoomLink())
                    .dateAndTime(event.getDateAndTime())
                    .eventCode(event.getEventCode())
                    .build();
        } else {
            throw new EventNotFoundException("Event with code " + eventCode + " not found at getEventDetails() method.");
        }
    }

    public void sendFinalConfirmationEmailForEventBooking(String userEmail, String eventCode)
            throws MyUserAccountNotExistsException {
        // Email content for client
//        String clientName;
//        if (myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail) == 1) {
//            clientName = studentService.getStudentByWorkEmail(userEmail).getStudentName();
//        } else if (myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail) == 2) {
//            ClientAccount account = clientAccountService.getAccountByEmail(userEmail);
//            clientName = account.getClientFirstName() + " " + account.getClientMiddleName() + " "  + account.getClientLastName();
//        } else {
//            throw new MyUserAccountNotExistsException("MyUser has no account at getOrderRequestZoomSession() method");
//        }
//        Optional<Event> checkEvent = eventRepository.findByEventCode(eventCode);
//        String clientSubject = "Seat booked for Event: " + checkEvent.get().getEventName();
//        String clientText = String.format(
//                """
//                        Dear %s,
//                        Your seat for the event successfully booked.
//
//                        Following are the event details:-
//                        Event Name:
//
//
//                        Have a great session. We look forward to hearing from you.
//
//                        Best regards,
//                        GuidebookX Team"""
//        ,clientName, );
//    }
//
//        // Send emails to client and student
//        emailServiceImpl.sendSimpleMessage(clientEmail, clientSubject, clientText);
//    }
        return;
    }
}
