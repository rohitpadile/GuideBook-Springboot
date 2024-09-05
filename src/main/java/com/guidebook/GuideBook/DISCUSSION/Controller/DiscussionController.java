package com.guidebook.GuideBook.DISCUSSION.Controller;

import com.guidebook.GuideBook.DISCUSSION.Service.DiscussionService;
import com.guidebook.GuideBook.DISCUSSION.dtos.AddDiscussionRequest;
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
@RequestMapping("/api/v1/discuss/")
public class DiscussionController {
    private final DiscussionService discussionService;
    @Autowired
    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @PostMapping("/addDiscussion")
    public ResponseEntity<Void> addDiscussion(@RequestBody AddDiscussionRequest addDiscussionRequest){
        discussionService.addDiscussion(addDiscussionRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
