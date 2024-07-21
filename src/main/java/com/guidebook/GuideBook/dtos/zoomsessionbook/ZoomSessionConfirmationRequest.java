package com.guidebook.GuideBook.dtos.zoomsessionbook;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class ZoomSessionConfirmationRequest {
    @NotNull
    private String studentWorkEmail;//TO SEND THE EMAIL TO THE STUDENT WITH ATTACHED FORM.
    @NotNull
    private String zoomSessionFormId; //TO GET ALL DETAILS ABOUT THE CLIENT
}
