package com.guidebook.GuideBook.DISCUSSION.Repository;

import com.guidebook.GuideBook.DISCUSSION.Model.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<Discussion, String> {

}
