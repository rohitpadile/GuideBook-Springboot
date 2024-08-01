package com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelZoomSessionFromClientRequest {
    @NotNull
    private String zoomSessionFormId;
    @NotNull
    private String studentWorkEmail;
}
