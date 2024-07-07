package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data //getters and setters
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String transactionId;

    @ManyToOne
    @JoinColumn(name = "fk_transactionId_clientEmail", referencedColumnName = "clientEmail")
    Client Client; //owning side

    Double transactionAmount; //RESEARCH FOR ITS DATA TYPE TO BE USED FOR LARGE SCALE APP
}
