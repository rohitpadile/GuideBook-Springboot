package com.guidebook.GuideBook.USER.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clientAccountId;

    @Column(unique = true, nullable = false)
    private String clientAccountEmail;

    private Long clientAccountZoomSessionCount;

    private Long clientAccountOfflineSessionCount;

    private Integer clientAccountSubscription_Monthly; //1 or 0 will determine if the monthly subscription is active or not

}
