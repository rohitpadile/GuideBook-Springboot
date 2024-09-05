package com.guidebook.GuideBook.DISCUSSION.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetDiscussionIdNameResponse {
    private String discussionId;
    private String discussionTitle;
    private String discussionOwner;
}
