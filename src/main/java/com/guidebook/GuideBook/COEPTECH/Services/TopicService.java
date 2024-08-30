package com.guidebook.GuideBook.COEPTECH.Services;

import com.guidebook.GuideBook.COEPTECH.Models.Topic;
import com.guidebook.GuideBook.COEPTECH.Repository.TopicRepository;
import com.guidebook.GuideBook.COEPTECH.dtos.AddNewTopicRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    @Autowired

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;

    }

    public Optional<Topic> getTopicById(Long topicId){
        return topicRepository.findById(topicId);
    }
    @Transactional
    public void addNewTopic(AddNewTopicRequest request) {
        Topic newTopic = new Topic();
        newTopic.setTitle(request.getTopicName());
        newTopic.setDescription(request.getTopicDescription());
        topicRepository.save(newTopic);
    }
}
