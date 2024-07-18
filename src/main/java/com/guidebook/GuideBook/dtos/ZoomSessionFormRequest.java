package com.guidebook.GuideBook.dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoomSessionFormRequest {
    @NotNull
    @NotEmpty
    private String clientFirstName;

    private String clientMiddleName;

    @NotNull
    @NotEmpty
    private String clientLastName;

    @NotNull
    @NotEmpty
    private String clientEmail;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\+?[0-9. ()-]{6,20}$", message = "Invalid phone number")
    private String clientPhoneNumber;

    @NotNull
    @Positive
    private Integer clientAge;

    @NotNull
    @NotEmpty
    private String clientCollege;

    @NotNull
    @NotEmpty
    private String clientProofDocLink;
}
