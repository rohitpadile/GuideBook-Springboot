package com.guidebook.GuideBook.USER.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "blacklisted_tokens")
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tokenId;

    @Column(nullable = false, unique = true)
    private String token;


    // getters and setters
}