package com.guidebook.GuideBook.dtos.zoomsessionbook;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class ConfirmZoomSessionRequestFromStudent {
    private String studentWorkEmail;
    private String ZoomSessionFormId;
}
