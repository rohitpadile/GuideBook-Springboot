package com.guidebook.GuideBook.MEETHOST.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guidebook.GuideBook.ADMIN.Models.College;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.USER.Models.MyUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventBookingTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;

    private Double transactionAmount;
    private String transactionStatus;
    private String userEmail;
    private String paymentOrderRzpId; //Directly use Razor pay order if instead of sql id
    private String eventCode;

    @ManyToOne
    @JoinColumn(name = "fk_transaction_useremail", referencedColumnName = "username")
    private MyUser myUser; //owning side

}
