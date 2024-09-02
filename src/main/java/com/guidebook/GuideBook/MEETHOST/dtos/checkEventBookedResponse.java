package com.guidebook.GuideBook.MEETHOST.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class checkEventBookedResponse {
    private Integer isBooked;
}
