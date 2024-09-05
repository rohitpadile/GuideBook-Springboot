package com.guidebook.GuideBook.DISCUSSION.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data

public class CommentDTO {
    private String userEmail;
    private String text;
    private String CommentId;
    private Date createdOn;
}
