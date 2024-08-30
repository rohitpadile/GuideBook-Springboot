package com.guidebook.GuideBook.COEPTECH.Services;

import com.guidebook.GuideBook.COEPTECH.Models.Comment;
import com.guidebook.GuideBook.COEPTECH.Models.Reply;
import com.guidebook.GuideBook.COEPTECH.Models.Topic;
import com.guidebook.GuideBook.COEPTECH.Repository.CommentRepository;
import com.guidebook.GuideBook.COEPTECH.Repository.ReplyRepository;
import com.guidebook.GuideBook.COEPTECH.exceptions.CommentNotFoundException;
import com.guidebook.GuideBook.COEPTECH.exceptions.ReplyNotFoundException;
import com.guidebook.GuideBook.COEPTECH.exceptions.TopicNotFoundException;
import com.guidebook.GuideBook.USER.Models.MyUser;
import com.guidebook.GuideBook.USER.Service.MyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DiscussionService {

    private final CommentService commentService;
    private final ReplyService replyService;
    private final TopicService topicService;
    private final MyUserService myUserService;
    @Autowired
    public DiscussionService(CommentService commentService,
                             ReplyService replyService,
                             TopicService topicService,
                             MyUserService myUserService) {
        this.commentService = commentService;
        this.replyService = replyService;
        this.myUserService= myUserService;
        this.topicService = topicService;
    }

    public Page<Comment> getComments(Long topicId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info(commentService.findByTopicId(topicId, pageable).toString());
        return commentService.findByTopicId(topicId, pageable);
    }
    public Page<Comment> findByTopicId(Long topicId, Pageable pageable) {
        return commentService.findByTopicId(topicId, pageable);
    }


    public Comment postComment(Long topicId, String text) throws TopicNotFoundException {
        Topic topic = topicService.getTopicById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found"));

        MyUser user = myUserService.getCurrentUser();  // Get the authenticated user

        Comment comment = new Comment();
        comment.setText(text);
        comment.setTopic(topic);
        comment.setUser(user);  // Associate the comment with the user

        return commentService.saveComment(comment);
    }

    public Reply postReply(Long topicId, Long commentId, Long parentReplyId, String text)
            throws CommentNotFoundException, ReplyNotFoundException, TopicNotFoundException {
        // Check if the topic exists
        Topic topic = topicService.getTopicById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found"));

        // Check if the comment exists
        Comment comment = commentService.getByCommentId(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        MyUser user = myUserService.getCurrentUser();  // Get the authenticated user

        Reply reply = new Reply();
        reply.setText(text);
        reply.setComment(comment);
        reply.setUser(user);  // Associate the reply with the user

        if (parentReplyId != null) {
            Reply parentReply = replyService.getReplyByReplyId(parentReplyId)
                    .orElseThrow(() -> new ReplyNotFoundException("Parent reply not found"));
            reply.setParentReply(parentReply);
        }

        return replyService.saveReply(reply);
    }
}
