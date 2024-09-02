package com.guidebook.GuideBook.MEETHOST.Repository;

import com.guidebook.GuideBook.MEETHOST.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    Ticket findByEventCode(String eventCode);
}
