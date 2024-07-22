package com.guidebook.GuideBook.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data //getters and setters
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String clientId;
    String clientFirstName;
    String clientMiddleName;
    String clientLastName;
    String clientEmail;
    //    @Pattern(regexp = "^\\+?[0-9. ()-]{6,20}$", message = "Invalid phone number")
    //PATTERN MATCHES FOR GENERAL NUMBER FOR ANY COUNTRY.
    String clientPhoneNumber;
    Integer clientAge;
    String clientCollege;
    //    @Lob //for storing large data
    String clientProofDocPath; //college id, college fees, government id. Path stored in frontend
    //VERY IMPORTANT NOTE:-
    //REJECT THE SESSION IF THE PROOF IS NOT VALID.
    //IF THE STUDENT CONFIRMS THE SESSION WITH A NON VALID PROOF, FIRE HIM/HER
    String clientPassword; //specify a Pattern that i shoudl contain one special char, etc...

    @OneToMany(mappedBy = "Client")
            @JsonIgnore
    List<ZoomSessionTransaction> clientZoomSessionTransactionList;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedOn;
    @Version
    private Integer version;
}