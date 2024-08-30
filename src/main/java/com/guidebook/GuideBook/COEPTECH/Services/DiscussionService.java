package com.guidebook.GuideBook.COEPTECH.Services;

import com.guidebook.GuideBook.COEPTECH.Models.Comment;
import com.guidebook.GuideBook.COEPTECH.Models.Reply;
import com.guidebook.GuideBook.COEPTECH.Repository.CommentRepository;
import com.guidebook.GuideBook.COEPTECH.Repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussionService {

    private final CommentService commentService;
    private final ReplyService replyService;

    @Autowired
    public DiscussionService(CommentService commentService, ReplyService replyService) {
        this.commentService = commentService;
        this.replyService = replyService;
    }

    public List<Comment> getComments(Long topicId, int page, int size) {
        return commentService.findAll(PageRequest.of(page, size)).getContent();
    }

    public Comment postComment(Long topicId, String text) {
        Comment comment = new Comment();
        comment.setText(text);
        return commentService.saveComment(comment);
    }

    public Reply postReply(Long commentId, Long parentReplyId, String text) {
        return replyService.saveReply(commentId, parentReplyId, text);
    }
}
