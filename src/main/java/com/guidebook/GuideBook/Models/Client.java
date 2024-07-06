package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data //getters and setters
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long clientId;
    String clientFirstName;
    String clientMiddleName;
    String clientLastName;
    @Column(unique = true)
    String clientEmail;
//    @Pattern(regexp = "^\\+?[0-9. ()-]{6,20}$", message = "Invalid phone number")
    //PATTERN MATCHES FOR GENERAL NUMBER FOR ANY COUNTRY.
    String clientPhoneNumber;
    Integer clientAge;
    String clientCollegeName;
    @Lob //for storing large data
    byte[] clientProofDoc; //college id, college fees, government id
    //VERY IMPORTANT NOTE:-
    //REJECT THE SESSION IF THE PROOF IS NOT VALID.
    //IF THE STUDENT CONFIRMS THE SESSION WITH A NON VALID PROOF, FIRE HIM/HER

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedOn;
    @Version
    private Integer version;
}