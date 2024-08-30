package com.guidebook.GuideBook.COEPTECH.Services;

import com.guidebook.GuideBook.COEPTECH.Models.Comment;
import com.guidebook.GuideBook.COEPTECH.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    // Method to fetch comments based on topicId with pagination
    public Page<Comment> findByTopicId(Long topicId, PageRequest pageRequest) {
        return commentRepository.findByTopicId(topicId, pageRequest);
    }

    // Method to save a new comment
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}

