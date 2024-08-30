package com.guidebook.GuideBook.COEPTECH.Controller;

import com.guidebook.GuideBook.COEPTECH.Services.TopicService;
import com.guidebook.GuideBook.COEPTECH.dtos.AddNewTopicRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/coeptech/discussion/")
public class TopicController {
    private final TopicService topicService;
    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }
    @PostMapping("/addNewTopic")
    public ResponseEntity<Void> addNewTopic(@RequestBody AddNewTopicRequest request){
        topicService.addNewTopic(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
