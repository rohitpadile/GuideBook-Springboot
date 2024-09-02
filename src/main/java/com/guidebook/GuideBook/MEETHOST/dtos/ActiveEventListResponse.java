package com.guidebook.GuideBook.MEETHOST.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ActiveEventListResponse {
    List<String> activeEventCodeList = new ArrayList<>();
}
