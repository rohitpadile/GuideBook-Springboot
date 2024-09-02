package com.guidebook.GuideBook.MEETHOST.Service;

import com.guidebook.GuideBook.MEETHOST.Repository.EventRepository;
import com.guidebook.GuideBook.MEETHOST.dtos.AddNewEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired

    public void addNewEvent(AddNewEventRequest request) {

    }
}
