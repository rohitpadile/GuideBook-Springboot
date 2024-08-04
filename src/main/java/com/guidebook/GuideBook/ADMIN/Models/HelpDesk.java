package com.guidebook.GuideBook.ADMIN.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HelpDesk {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String helpDeskId;
    private String helpDeskEmailSubject;
    private String helpDeskEmailSentFrom;
    private String helpDeskEmailSentFTo;
    @Lob
    private String helpDeskEmailContent;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date updatedOn;
    @Version
    @JsonIgnore
    private Integer version;
}
