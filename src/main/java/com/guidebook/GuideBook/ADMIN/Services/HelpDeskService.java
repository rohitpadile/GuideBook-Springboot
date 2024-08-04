package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Repository.HelpDeskRepository;
import com.guidebook.GuideBook.ADMIN.dtos.AddHelpDeskEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelpDeskService {
    private HelpDeskRepository helpDeskRepository;
    @Autowired
    public HelpDeskService(HelpDeskRepository helpDeskRepository) {
        this.helpDeskRepository = helpDeskRepository;
    }

    public void addHelpDeskEmail(AddHelpDeskEmailRequest addHelpDeskEmailRequest) {
//        String helpSubject = String.format("Help from %s", addHelpDeskEmailRequest.);
//        String helpContent = String.format(
//                        "At the end of the session, please give the feedback.\n" +
//                        "Important Note: Fill the form, then only the session will be counted in " +
//                        "student's account and he can provide more such sessions in the future." +
//                        "\nThank you for your co-operation. Have a great session\n\n" +
//                        "Feedback link: %s" +
//                        "\n\nIf you have any issues regarding the email, please send " +
//                        "an email to us at help.guidebookx@gmail.com" +
//                        "\n\nBest regards,\n" +
//                        "GuidebookX Team"
//                );
    }
}
