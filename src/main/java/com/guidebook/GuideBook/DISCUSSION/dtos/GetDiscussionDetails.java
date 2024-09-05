package com.guidebook.GuideBook.DISCUSSION.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetDiscussionDetails {
    private String discussionTitle;
}
