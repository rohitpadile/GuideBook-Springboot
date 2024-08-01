package com.guidebook.GuideBook.ADMIN.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Valid
@Data
public class AddStudentClassTypeRequest {
    @NotNull(message = "Student class type name cannot be null")
    private String studentClassTypeName;
}
