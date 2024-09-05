package com.guidebook.GuideBook.DISCUSSION.Repository;


import com.guidebook.GuideBook.DISCUSSION.Model.CommentV1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentV1, String> {
    List<CommentV1> findByDiscussion_DiscussionIdOrderByCreatedOnDesc(String discussionId);
}
