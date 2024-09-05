package com.guidebook.GuideBook.DISCUSSION.Service;

import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.DISCUSSION.Model.CommentV1;
import com.guidebook.GuideBook.DISCUSSION.Model.Discussion;
import com.guidebook.GuideBook.DISCUSSION.Repository.CommentRepository;
import com.guidebook.GuideBook.DISCUSSION.Repository.DiscussionRepository;
import com.guidebook.GuideBook.DISCUSSION.dtos.CommentDTO;
import com.guidebook.GuideBook.DISCUSSION.exceptions.CommentNotFoundException;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.MyUser;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Repository.MyUserRepository;
import com.guidebook.GuideBook.USER.Service.ClientAccountService;
import com.guidebook.GuideBook.USER.Service.MyUserService;
import com.guidebook.GuideBook.USER.Service.StudentMentorAccountService;
import com.guidebook.GuideBook.USER.exceptions.MyUserAccountNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final DiscussionRepository discussionRepository;
//    private final MyUserRepository userRepository;
    private final MyUserService myUserService;
    private final ClientAccountService clientAccountService;
    private final StudentMentorAccountService studentMentorAccountService;
    private final StudentService studentService;
    @Autowired
    public CommentService(CommentRepository commentRepository,
                          DiscussionRepository discussionRepository,
//                          MyUserRepository userRepository,
                          StudentMentorAccountService studentMentorAccountService,
                          ClientAccountService clientAccountService,
                          MyUserService myUserService,
                          StudentService studentService

                          ) {
        this.commentRepository = commentRepository;
        this.studentService  = studentService;
//        this.userRepository = userRepository;
        this.discussionRepository = discussionRepository;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
        this.myUserService = myUserService;

    }


    // Get all comments for a discussion
    public List<CommentDTO> getCommentsForDiscussion(String discussionId) {
        List<CommentV1> comments = commentRepository.findByDiscussion_DiscussionIdOrderByCreatedOnDesc(discussionId);
        log.info("I am retrieving comments");
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Add a new comment
    public CommentDTO addComment(String discussionId, CommentDTO commentDTO, String userEmail)
            throws MyUserAccountNotExistsException {
        MyUser user = myUserService.getMyUserRepository().findByUsername(userEmail);
        if(user==null){
            throw new MyUserAccountNotExistsException("User not found");
        }

        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found"));

        CommentV1 comment = new CommentV1();
        comment.setText(commentDTO.getText());
        comment.setUser(user);
        comment.setDiscussion(discussion);
        comment.setIsVisible(1);
        CommentV1 savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    // Convert Comment entity to CommentDTO
    private CommentDTO convertToDTO(CommentV1 comment) {
        CommentDTO dto = new CommentDTO();
        if(comment.getIsVisible() == 1){
            dto.setCommentId(comment.getCommentId());
            dto.setText(comment.getText());
            String userName;
            Integer type = myUserService.checkUserEmailAccountTypeGeneralPurpose(comment.getUser().getUsername());
            if(type == 1){
                userName = studentService.getStudentByWorkEmail(comment.getUser().getUsername()).getStudentName();
            } else if(type == 2){
                ClientAccount account = clientAccountService.getAccountByEmail(comment.getUser().getUsername());
                userName = (account.getClientFirstName().isEmpty() ? "Anonymous" : "");
                if(userName.isEmpty()){
                    userName = account.getClientFirstName() + (account.getClientMiddleName().isEmpty() ? "" : " " + account.getClientMiddleName()) + " " + account.getClientLastName();
                }
            } else {
                userName = "Anonymous";
            }
            dto.setUserName(userName);
            dto.setUserEmail(comment.getUser().getUsername());
            dto.setCreatedOn(comment.getCreatedOn());
            return dto;
        } else{
            return null;
        }
    }

    // Method to delete a comment by updating isVisible to 0
    // Method to delete a comment by updating isVisible to 0
    public void deleteComment(String commentId) throws CommentNotFoundException {
        CommentV1 comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + commentId));
        comment.setIsVisible(0);
        commentRepository.save(comment);
    }
}
