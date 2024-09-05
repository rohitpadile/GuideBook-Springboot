package com.guidebook.GuideBook.DISCUSSION.Controller;

import com.guidebook.GuideBook.DISCUSSION.Service.CommentService;
import com.guidebook.GuideBook.DISCUSSION.dtos.CommentDTO;
import com.guidebook.GuideBook.USER.exceptions.MyUserAccountNotExistsException;
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
public class CommentController {
    private final CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    // Get all comments for a discussion
    @GetMapping("/{discussionId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsForDiscussion(@PathVariable String discussionId) {
        List<CommentDTO> comments = commentService.getCommentsForDiscussion(discussionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Add a new comment to a discussion
    @PostMapping("/{discussionId}/comments")
    public ResponseEntity<CommentDTO> addComment( @PathVariable String discussionId,
            @RequestBody CommentDTO commentDTO)
            throws MyUserAccountNotExistsException {
        CommentDTO savedComment = commentService.addComment(discussionId, commentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
