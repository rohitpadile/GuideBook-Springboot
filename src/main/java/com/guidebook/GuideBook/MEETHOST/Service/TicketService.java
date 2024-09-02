package com.guidebook.GuideBook.MEETHOST.Service;

import com.guidebook.GuideBook.MEETHOST.Enum.TicketStatus;
import com.guidebook.GuideBook.MEETHOST.Model.Ticket;
import com.guidebook.GuideBook.MEETHOST.Repository.TicketRepository;
import com.guidebook.GuideBook.MEETHOST.dtos.AddNewEventRequest;
import com.guidebook.GuideBook.MEETHOST.dtos.TicketResponse;
import com.guidebook.GuideBook.USER.Models.MyUser;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;
    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    @Transactional //search the ticket by EVENTCODE
    public void createNewTicket(AddNewEventRequest request){
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getEventName());
        ticket.setDescription(request.getTicketDescription());
        ticket.setTicketStatus(TicketStatus.OPEN.toString());
        ticket.setEventCode(request.getEventCode());
        ticket.setDateAndTime(request.getDateAndTime());
        ticket.setEventLocation(request.getEventLocation());
        ticketRepository.save(ticket);
    }

    public Ticket getTicketByEventCode(String eventCode){
        return ticketRepository.findByEventCode(eventCode);
    }
    @Transactional
    public List<TicketResponse> getBookedTicketsForUser(MyUser user) {
        List<TicketResponse> responses = new ArrayList<>();

        for(Ticket ticket : user.getTicketList()){
//            log.info("Ticket: {}" , ticket); //DONT UNCOMMENT THIS. CREATES AN ERROR WHILE PARSING
            if(ticket.getTicketStatus().equals(TicketStatus.OPEN.toString())){
                TicketResponse ticketResponse = new TicketResponse();
                ticketResponse.setTitle(ticket.getTitle());
                ticketResponse.setDateAndTime(ticket.getDateAndTime());
                ticketResponse.setEventLocation(ticket.getEventLocation());
                responses.add(ticketResponse);
            }
        }
        return responses;
    }
}
