package com.guidebook.GuideBook.MEETHOST.Service;

import com.guidebook.GuideBook.MEETHOST.Enum.TicketStatus;
import com.guidebook.GuideBook.MEETHOST.Model.Ticket;
import com.guidebook.GuideBook.MEETHOST.Repository.TicketRepository;
import com.guidebook.GuideBook.MEETHOST.dtos.AddNewEventRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    @Transactional
    public void createNewTicket(AddNewEventRequest request){
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getEventName());
        ticket.setDescription(request.getTicketDescription());
        ticket.setTicketStatus(TicketStatus.OPEN.toString());
        ticketRepository.save(ticket);
    }
}
