package com.guidebook.GuideBook.COEPTECH.Controller;

import com.guidebook.GuideBook.COEPTECH.Models.Comment;
import com.guidebook.GuideBook.COEPTECH.Models.Reply;
import com.guidebook.GuideBook.COEPTECH.Services.DiscussionService;
import com.guidebook.GuideBook.COEPTECH.dtos.CommentRequest;
import com.guidebook.GuideBook.COEPTECH.dtos.ReplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Comment> getComments(@PathVariable Long topicId,
                                     @RequestParam int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return discussionService.getComments(topicId, page, size);
    }

    @PostMapping
    public Comment postComment(@PathVariable Long topicId, @RequestBody CommentRequest request) {
        return discussionService.postComment(topicId, request.getText());
    }

    @PostMapping("/{commentId}/replies")
    public Reply postReply(@PathVariable Long topicId,
                           @PathVariable Long commentId,
                           @RequestParam(required = false) Long parentReplyId,
                           @RequestBody ReplyRequest request) {
        return discussionService.postReply(commentId, parentReplyId, request.getText());
    }
}
