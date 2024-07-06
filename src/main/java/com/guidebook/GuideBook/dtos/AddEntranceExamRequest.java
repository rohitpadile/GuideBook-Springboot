package com.guidebook.GuideBook.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Valid
@Data
public class AddEntranceExamRequest {
    @NotNull(message = "Entrance Exam name should not be null")
    private String examName;
}
