package com.guidebook.GuideBook.TR.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TRUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trUserId;

    private String trUserFirstName;
    private String trUserLastName;
    private String trUserPassword;

}
