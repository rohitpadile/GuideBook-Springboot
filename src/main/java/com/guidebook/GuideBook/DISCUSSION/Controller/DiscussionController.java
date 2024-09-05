package com.guidebook.GuideBook.DISCUSSION.Controller;

import com.guidebook.GuideBook.DISCUSSION.Service.DiscussionService;
import com.guidebook.GuideBook.DISCUSSION.dtos.AddDiscussionRequest;
import com.guidebook.GuideBook.DISCUSSION.dtos.GetDiscussionDetails;
import com.guidebook.GuideBook.DISCUSSION.dtos.GetDiscussionIdNameResponse;
import com.guidebook.GuideBook.DISCUSSION.exceptions.DiscussionNotFoundException;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/discuss/")
public class DiscussionController {
    private final JwtUtil jwtUtil;
    private final DiscussionService discussionService;
    @Autowired
    public DiscussionController(DiscussionService discussionService,
                                JwtUtil jwtUtil) {
        this.discussionService = discussionService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/addDiscussion")
    public ResponseEntity<Void> addDiscussion(@RequestBody AddDiscussionRequest addDiscussionRequest){
        discussionService.addDiscussion(addDiscussionRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getDiscussionDetails")
    public ResponseEntity<GetDiscussionDetails> getDiscussionDetails(
            @RequestParam String discussionId
    ) throws DiscussionNotFoundException {
        GetDiscussionDetails res = discussionService.getDiscussionDetails(discussionId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/getDiscussionList")
    public ResponseEntity<List<GetDiscussionIdNameResponse>> getDiscussionList(){
        List<GetDiscussionIdNameResponse> res = discussionService.getDiscussionList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/createDiscussionPageFromUser")
    public ResponseEntity<Void> createDiscussionPageFromUser(
            @RequestBody AddDiscussionRequest addDiscussionRequest,
            HttpServletRequest request){
        String userEmail = jwtUtil.extractEmailFromToken(request);
        discussionService.addDiscussionFromUser(addDiscussionRequest, userEmail);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
