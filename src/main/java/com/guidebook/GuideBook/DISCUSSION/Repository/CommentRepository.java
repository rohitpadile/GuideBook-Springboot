package com.guidebook.GuideBook.DISCUSSION.Repository;

import com.guidebook.GuideBook.DISCUSSION.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findByDiscussionDiscussionIdOrderByCreatedOnDesc(String discussionId);
}
