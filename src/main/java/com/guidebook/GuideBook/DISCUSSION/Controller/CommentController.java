package com.guidebook.GuideBook.DISCUSSION.Controller;

import com.guidebook.GuideBook.DISCUSSION.Service.CommentService;
import com.guidebook.GuideBook.DISCUSSION.dtos.CommentDTO;
import com.guidebook.GuideBook.DISCUSSION.exceptions.CommentNotFoundException;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import com.guidebook.GuideBook.USER.exceptions.MyUserAccountNotExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com", "https://a.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/discuss/")
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;
    @Autowired
    public CommentController(CommentService commentService,
                             JwtUtil jwtUtil) {
        this.commentService = commentService;
        this.jwtUtil = jwtUtil;
    }


    // Get all comments for a discussion
    @GetMapping("/{discussionId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsForDiscussion(@PathVariable String discussionId) {
        List<CommentDTO> comments = commentService.getCommentsForDiscussion(discussionId);
        log.info("Comments returned: {}", comments);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Add a new comment to a discussion
    @PostMapping("/addComment")
    public ResponseEntity<CommentDTO> addComment(
            @RequestParam String discussionId, // Use @RequestParam instead of @PathVariable
            @RequestBody @Valid CommentDTO commentDTO,
            HttpServletRequest request) throws MyUserAccountNotExistsException {
        log.info("I am into adding comment: {}", commentDTO);
        String userEmail = jwtUtil.extractEmailFromToken(request);
        CommentDTO savedComment = commentService.addComment(discussionId, commentDTO, userEmail);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    // Delete a comment by setting isVisible to 0
    @GetMapping("/deleteComment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) throws CommentNotFoundException {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
