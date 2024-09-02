package com.guidebook.GuideBook.MEETHOST.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guidebook.GuideBook.ADMIN.Models.College;
import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.USER.Models.MyUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String eventId;
    private String eventName;
    private String eventType;//use enum.toString
    private String eventDescription;
    private String eventLocation;
    private String registrationLink;
    private boolean isOnline;
    private String organizer;
    private String zoomLink;
    private String dateAndTime; //Set manual date and time
    @ManyToMany(mappedBy = "eventList")
    @JsonIgnore
    private Set<MyUser> eventUserList = new HashSet<>();

}
