package com.guidebook.GuideBook.COEPTECH.Repository;

import com.guidebook.GuideBook.COEPTECH.Models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByTopicId(Long topicId, PageRequest pageRequest);
}
