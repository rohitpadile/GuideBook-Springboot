package com.guidebook.GuideBook.MEETHOST.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guidebook.GuideBook.USER.Models.MyUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
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
    private Integer isActive;
    private String organizer;
    private String zoomLink;
    private String dateAndTime; //Set manual date and time

    private String eventCode;
    @ManyToMany(mappedBy = "eventList")
    @JsonIgnore
    private Set<MyUser> eventUserList = new HashSet<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date updatedOn;
//    @Version
//    @JsonIgnore
//    private Integer version;
}
