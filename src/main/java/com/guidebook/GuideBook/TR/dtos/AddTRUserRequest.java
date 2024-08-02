package com.guidebook.GuideBook.TR.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddTRUserRequest {
    @NotNull
    private String trUserFirstName;
    @NotNull
    private String trUserLastName;
    @NotNull
    private String trUserPassword;
    @NotNull
    private String trAdminPassword;
}
