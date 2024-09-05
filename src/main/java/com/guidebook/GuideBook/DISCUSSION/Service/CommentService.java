package com.guidebook.GuideBook.DISCUSSION.Service;

import com.guidebook.GuideBook.DISCUSSION.Model.Comment;
import com.guidebook.GuideBook.DISCUSSION.Model.Discussion;
import com.guidebook.GuideBook.DISCUSSION.Repository.CommentRepository;
import com.guidebook.GuideBook.DISCUSSION.Repository.DiscussionRepository;
import com.guidebook.GuideBook.DISCUSSION.dtos.CommentDTO;
import com.guidebook.GuideBook.USER.Models.MyUser;
import com.guidebook.GuideBook.USER.Repository.MyUserRepository;
import com.guidebook.GuideBook.USER.exceptions.MyUserAccountNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final DiscussionRepository discussionRepository;
    private final MyUserRepository userRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository,
                          DiscussionRepository discussionRepository,
                          MyUserRepository userRepository
                          ) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.discussionRepository = discussionRepository;
    }


    // Get all comments for a discussion
    public List<CommentDTO> getCommentsForDiscussion(String discussionId) {
        List<Comment> comments = commentRepository.findByDiscussionDiscussionIdOrderByCreatedOnDesc(discussionId);
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Add a new comment
    public CommentDTO addComment(String discussionId, CommentDTO commentDTO)
            throws MyUserAccountNotExistsException {
        MyUser user = userRepository.findByUsername(commentDTO.getUserEmail());
        if(user==null){
            throw new MyUserAccountNotExistsException("User not found");
        }

        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found"));

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setUser(user);
        comment.setDiscussion(discussion);

        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    // Convert Comment entity to CommentDTO
    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setCommentId(comment.getCommentId());
        dto.setText(comment.getText());
        dto.setUserEmail(comment.getUser().getUsername());
        dto.setCreatedOn(comment.getCreatedOn());
        return dto;
    }
}
