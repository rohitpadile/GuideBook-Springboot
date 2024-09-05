package com.guidebook.GuideBook.DISCUSSION.Service;

import com.guidebook.GuideBook.DISCUSSION.Model.Discussion;
import com.guidebook.GuideBook.DISCUSSION.Repository.DiscussionRepository;
import com.guidebook.GuideBook.DISCUSSION.dtos.AddDiscussionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository) {
        this.discussionRepository = discussionRepository;
    }

    public void addDiscussion(AddDiscussionRequest addDiscussionRequest) {
        Discussion discussion = new Discussion();
        discussion.setTitle(addDiscussionRequest.getDiscussionTitle());
        discussionRepository.save(discussion);
    }
}
