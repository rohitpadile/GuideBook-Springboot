package com.guidebook.GuideBook.COEPTECH.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ReplyRequest {
    @NotNull
    private String text;

}

