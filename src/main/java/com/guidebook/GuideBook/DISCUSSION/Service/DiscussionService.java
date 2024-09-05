package com.guidebook.GuideBook.DISCUSSION.Service;

import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.DISCUSSION.Model.Discussion;
import com.guidebook.GuideBook.DISCUSSION.Repository.DiscussionRepository;
import com.guidebook.GuideBook.DISCUSSION.dtos.AddDiscussionRequest;
import com.guidebook.GuideBook.DISCUSSION.dtos.GetDiscussionDetails;
import com.guidebook.GuideBook.DISCUSSION.dtos.GetDiscussionIdNameResponse;
import com.guidebook.GuideBook.DISCUSSION.exceptions.DiscussionNotFoundException;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.MyUser;
import com.guidebook.GuideBook.USER.Repository.MyUserRepository;
import com.guidebook.GuideBook.USER.Service.ClientAccountService;
import com.guidebook.GuideBook.USER.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final MyUserService myUserService;
    private final StudentService studentService;
    private final ClientAccountService clientAccountService;
    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository,
                             MyUserService myUserService,
                             StudentService studentService,
                             ClientAccountService clientAccountService) {
        this.discussionRepository = discussionRepository;
        this.myUserService = myUserService;
        this.clientAccountService = clientAccountService;
        this.studentService = studentService;
    }

    public void addDiscussion(AddDiscussionRequest addDiscussionRequest) {
        Discussion discussion = new Discussion();
        discussion.setTitle(addDiscussionRequest.getDiscussionTitle());
        discussionRepository.save(discussion);
    }

    public GetDiscussionDetails getDiscussionDetails(String discussionId)
            throws DiscussionNotFoundException {
        Optional<Discussion> discussion = discussionRepository.findById(discussionId);
        if(discussion.isPresent()){
            return GetDiscussionDetails.builder()
                    .discussionTitle(discussion.get().getTitle())
                    .build();
        } else{
            throw new DiscussionNotFoundException("discussion absent at getDiscussionDetails() method");
        }

    }

    public List<GetDiscussionIdNameResponse> getDiscussionList() {
        List<GetDiscussionIdNameResponse> res = new ArrayList<>();
        for(Discussion discussion : discussionRepository.findAll()){
            Integer type = myUserService.checkUserEmailAccountTypeGeneralPurpose(discussion.getMyUser().getUsername());
            String userName;
            if(type == 1) userName = studentService.getStudentByWorkEmail(discussion.getMyUser().getUsername()).getStudentName();
            else if(type == 2) {
                ClientAccount account = clientAccountService.getAccountByEmail(discussion.getMyUser().getUsername());
                userName = account.getClientFirstName() + " "  + (account.getClientMiddleName().isEmpty() ? "" : account.getClientMiddleName()  + " ") + account.getClientLastName();
                if(account.getClientFirstName().isEmpty()){
                    userName = "Anonymous";
                }
            }  else userName = "Anonymous";
            res.add(
                    GetDiscussionIdNameResponse.builder()
                            .discussionTitle(discussion.getTitle())
                            .discussionId(discussion.getDiscussionId())
                            .discussionOwner(userName)
                            .build()
            );
        }
        return res;

    }

    public void addDiscussionFromUser(AddDiscussionRequest addDiscussionRequest, String userEmail) {
        Discussion discussion = new Discussion();
        discussion.setTitle(addDiscussionRequest.getDiscussionTitle());
        MyUser user = myUserService.getMyUserRepository().findByUsername(userEmail);
        discussion.setMyUser(user);
        discussionRepository.save(discussion);
    }
}
