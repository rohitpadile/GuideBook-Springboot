package com.guidebook.GuideBook.MEETHOST.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ActivateEventsWithCodeRequest {
    List<String> eventCodeList;
}
