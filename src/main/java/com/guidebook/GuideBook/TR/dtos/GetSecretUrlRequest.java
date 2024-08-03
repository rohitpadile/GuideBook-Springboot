package com.guidebook.GuideBook.TR.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetSecretUrlRequest {
    private String trUserFirstName;
    private String trUserLastName;
    private String trAdminPassword;

}
