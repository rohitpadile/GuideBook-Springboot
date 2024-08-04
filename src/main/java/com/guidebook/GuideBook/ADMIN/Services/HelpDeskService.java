//package com.guidebook.GuideBook.ADMIN.Services;
//
//import com.guidebook.GuideBook.ADMIN.Repository.HelpDeskRepository;
//import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
//import com.guidebook.GuideBook.ADMIN.dtos.AddHelpDeskEmailRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class HelpDeskService {
//    @Value("${helpdeskemailaddress}")
//    private String helpDeskEmailAddress;
//    private HelpDeskRepository helpDeskRepository;
//    private EmailServiceImpl emailServiceImpl;
//    @Autowired
//    public HelpDeskService(HelpDeskRepository helpDeskRepository,
//                           EmailServiceImpl emailServiceImpl) {
//        this.helpDeskRepository = helpDeskRepository;
//        this.emailServiceImpl = emailServiceImpl;
//    }
//
//    public void addHelpDeskEmail(AddHelpDeskEmailRequest addHelpDeskEmailRequest) {
//        String helpSubject = String.format("Help from %s: %s",
//                addHelpDeskEmailRequest.getHelpDeskFirstName() +
//                " " + addHelpDeskEmailRequest.getHelpDeskLastName(),
//                addHelpDeskEmailRequest.getHelpDeskEmailSubject());
//        String helpContent = String.format(
//                """
//                        Subject: %s
//
//                        Message: %s
//
//                        """,
//                addHelpDeskEmailRequest.getHelpDeskEmailSubject(),
//                addHelpDeskEmailRequest.getHelpDeskEmailContent()
//                );
//        emailServiceImpl.sendSimpleMessage( this.helpDeskEmailAddress , helpSubject, helpContent);
//
//    }
//}
