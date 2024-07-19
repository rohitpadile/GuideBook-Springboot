package com.guidebook.GuideBook.dtos.zoomsessionform;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ZoomSessionFormMessageResponse {
    private String zoomSessionFormMessage;
    private Integer zoomSessionFormMessageCode;
    private String zoomSessionFormId;
}
