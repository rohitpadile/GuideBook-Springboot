package com.guidebook.GuideBook.COEPTECH.Controller;

import com.guidebook.GuideBook.COEPTECH.Models.Comment;
import com.guidebook.GuideBook.COEPTECH.Models.Reply;
import com.guidebook.GuideBook.COEPTECH.Services.DiscussionService;
import com.guidebook.GuideBook.COEPTECH.dtos.CommentRequest;
import com.guidebook.GuideBook.COEPTECH.dtos.ReplyRequest;
import com.guidebook.GuideBook.COEPTECH.exceptions.CommentNotFoundException;
import com.guidebook.GuideBook.COEPTECH.exceptions.ReplyNotFoundException;
import com.guidebook.GuideBook.COEPTECH.exceptions.TopicNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/coeptech/discussion/")
public class DiscussionController {

    private final DiscussionService discussionService;

    @Autowired
    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @GetMapping("/{topicId}/comments")
    public Page<Comment> getComments(@PathVariable Long topicId,
                                     @RequestParam int page,
                                     @RequestParam(defaultValue = "10") int size) {

        return discussionService.getComments(topicId, page, size);
    }

    @PostMapping("/{topicId}/comments")
    public Comment postComment(@PathVariable Long topicId,
                               @RequestBody CommentRequest request)
            throws TopicNotFoundException {
        return discussionService.postComment(topicId, request.getText());
    }

    @PostMapping("/{topicId}/comments/{commentId}/replies")
    public Reply postReply(@PathVariable Long topicId,
                           @PathVariable Long commentId,
                           @RequestParam(required = false) Long parentReplyId,
                           @RequestBody ReplyRequest request)
            throws ReplyNotFoundException, CommentNotFoundException, TopicNotFoundException {
        return discussionService.postReply(topicId, commentId, parentReplyId, request.getText());
    }
}
