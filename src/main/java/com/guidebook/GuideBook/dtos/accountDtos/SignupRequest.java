package com.guidebook.GuideBook.dtos.accountDtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Valid
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRequest {
    @NotNull
    String clientFirstName;

    String clientMiddleName;
    @NotNull
    String clientLastName;
    @NotNull
    String clientEmail;
    @NotNull
    String clientPhoneNumber;
    @Positive
    Integer clientAge;
    @NotNull
    String clientCollege;
    @NotNull
    String clientProofDocPath;
    @NotNull
    String clientPassword;
}
