package com.guidebook.GuideBook.COEPTECH.dtos;

import lombok.Data;

@Data
public class AddNewTopicRequest {
    private String topicName;
    private String topicDescription;
}
