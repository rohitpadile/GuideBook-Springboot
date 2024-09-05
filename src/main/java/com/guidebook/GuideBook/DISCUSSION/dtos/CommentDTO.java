package com.guidebook.GuideBook.DISCUSSION.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data

public class CommentDTO {
    private String userEmail;
    private String userName;
    @NotNull
    private String text;
    private String CommentId;
    private Date createdOn;
}
