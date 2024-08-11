package com.guidebook.GuideBook.USER.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckUserEmailAccountTypeRequest {
    @NotNull
    private String userEmail;

}
