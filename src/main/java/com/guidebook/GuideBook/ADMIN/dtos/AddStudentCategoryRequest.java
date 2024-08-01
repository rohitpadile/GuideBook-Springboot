package com.guidebook.GuideBook.ADMIN.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Valid
public class AddStudentCategoryRequest {
    @NotNull(message = "Student category should not be null")
    private String studentCategory;
}
