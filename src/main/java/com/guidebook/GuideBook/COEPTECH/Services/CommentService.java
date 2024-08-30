package com.guidebook.GuideBook.COEPTECH.Services;

import com.guidebook.GuideBook.COEPTECH.Models.Comment;
import com.guidebook.GuideBook.COEPTECH.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    // Method to fetch comments based on topicId with pagination
    public Page<Comment> findByTopicId(Long topicId, Pageable pageable) {
        return commentRepository.findByTopicId(topicId, (PageRequest) pageable);
    }


    // Method to save a new comment
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Optional<Comment> getByCommentId(Long commentId) {
        return commentRepository.findById(commentId);
    }
}

