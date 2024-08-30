package com.guidebook.GuideBook.COEPTECH.Repository;

import com.guidebook.GuideBook.COEPTECH.Models.Comment;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByTopicId(Long topicId, Pageable pageable);

}
