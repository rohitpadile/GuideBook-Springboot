package com.guidebook.GuideBook.Services.zoomsessionbook;

import com.guidebook.GuideBook.Models.ZoomSessionForm;
import com.guidebook.GuideBook.Repository.ZoomSessionFormRepository;
import com.guidebook.GuideBook.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.dtos.zoomsessionbook.ZoomSessionConfirmationRequest;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionFormMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoomSessionBookService { //HANDLES FROM CONFIRMATION PART FROM THE STUDENT
    private final EmailServiceImpl emailServiceImpl;
    private final ZoomSessionFormRepository zoomSessionFormRepository;
    @Autowired
    public ZoomSessionBookService(ZoomSessionFormRepository zoomSessionFormRepository,
                                  EmailServiceImpl emailServiceImpl) {
        this.zoomSessionFormRepository = zoomSessionFormRepository;
        this.emailServiceImpl = emailServiceImpl;
    }


    public void handleZoomSessionFormSuccess(ZoomSessionConfirmationRequest request) {
        // Retrieve the form details from the database
        ZoomSessionForm form = zoomSessionFormRepository.findByZoomSessionFormId(request.getZoomSessionFormId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid form ID"));
        //THROW FORM NOT FOUND EXCEPTION HERE

        // Prepare the email content
        String emailContent = prepareEmailContent(form);

        // Send the email to the student
        emailServiceImpl.sendSimpleMessage(request.getStudentWorkEmail(),
                "Zoom Session Request from " +
                        form.getClientFirstName() +
                        form.getClientLastName(),
                emailContent);
        form.setIsVerified(1); //FORM IS MARKED AS VERIFIED SO NOT TO DELETE IT VIA SCHEDULED TASKS
        zoomSessionFormRepository.save(form);
    }

    private String prepareEmailContent(ZoomSessionForm form) {
        // Prepare the email content with form details
        StringBuilder content = new StringBuilder();
        content.append("Dear Student,\n\n");
        content.append("New request for Zoom session. Here are the details:\n\n");
        content.append("First Name: ").append(form.getClientFirstName()).append("\n");
        content.append("Middle Name: ").append(form.getClientMiddleName()).append("\n");
        content.append("Last Name: ").append(form.getClientLastName()).append("\n");
        content.append("Email: ").append(form.getClientEmail()).append("\n");
        content.append("Phone Number: ").append(form.getClientPhoneNumber()).append("\n");
        content.append("Age: ").append(form.getClientAge()).append("\n");
        content.append("College: ").append(form.getClientCollege()).append("\n");
        content.append("Proof Document Link: ").append(form.getClientProofDocLink()).append("\n");
        content.append("\nPlease confirm your availability for the Zoom session by clicking on this link below:\n\n");
        content.append("Best regards,\n");
        content.append("GuideBookX Team");
        return content.toString();
    }
}
