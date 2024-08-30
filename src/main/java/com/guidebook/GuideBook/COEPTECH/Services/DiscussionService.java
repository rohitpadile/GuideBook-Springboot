package com.guidebook.GuideBook.COEPTECH.Services;

import com.guidebook.GuideBook.COEPTECH.Models.Comment;
import com.guidebook.GuideBook.COEPTECH.Models.Reply;
import com.guidebook.GuideBook.COEPTECH.Models.Topic;
import com.guidebook.GuideBook.COEPTECH.Repository.CommentRepository;
import com.guidebook.GuideBook.COEPTECH.Repository.ReplyRepository;
import com.guidebook.GuideBook.COEPTECH.exceptions.TopicNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {

    private final CommentService commentService;
    private final ReplyService replyService;
    private final TopicService topicService;
    @Autowired
    public DiscussionService(CommentService commentService,
                             ReplyService replyService,
                             TopicService topicService) {
        this.commentService = commentService;
        this.replyService = replyService;
        this.topicService = topicService;
    }

    public List<Comment> getComments(Long topicId, int page, int size) {
        // Fetch comments based on topicId
        return commentService.findByTopicId(topicId, PageRequest.of(page, size)).getContent();
    }

    public Comment postComment(Long topicId, String text)
            throws TopicNotFoundException {
        Optional<Topic> checkTopic = topicService.getTopicById(topicId);
        if(checkTopic.isPresent()){
            Topic topic = checkTopic.get();
            Comment comment = new Comment();
            comment.setTopic(topic);  // Assuming Comment entity has a topicId field
            comment.setText(text);
            return commentService.saveComment(comment);
        }else {
            throw new TopicNotFoundException("Topic not found at postComment() method");
        }

    }

    public Reply postReply(Long commentId, Long parentReplyId, String text) {
        return replyService.saveReply(commentId, parentReplyId, text);
    }
}
