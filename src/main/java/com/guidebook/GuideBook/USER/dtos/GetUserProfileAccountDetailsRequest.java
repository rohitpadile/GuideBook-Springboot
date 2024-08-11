package com.guidebook.GuideBook.USER.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetUserProfileAccountDetailsRequest {
    @NotNull
    private String userEmail;
}
