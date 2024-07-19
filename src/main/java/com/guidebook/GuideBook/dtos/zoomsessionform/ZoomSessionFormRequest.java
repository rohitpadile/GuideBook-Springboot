package com.guidebook.GuideBook.dtos.zoomsessionform;


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
    private String clientFirstName;

    private String clientMiddleName;

    @NotNull
    private String clientLastName;

    @NotNull
    private String clientEmail;

    @NotNull
    @Pattern(regexp = "^\\+?[0-9. ()-]{6,20}$", message = "Invalid phone number")
    private String clientPhoneNumber;

    @NotNull
    @Positive
    private Integer clientAge;

    @NotNull
    private String clientCollege;

    @NotNull
    private String clientProofDocLink;
}
