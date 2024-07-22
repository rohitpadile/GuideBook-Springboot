package com.guidebook.GuideBook.Services.zoomsessionbook;

import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Models.ZoomSessionForm;
import com.guidebook.GuideBook.Repository.StudentRepository;
import com.guidebook.GuideBook.Repository.ZoomSessionFormRepository;
import com.guidebook.GuideBook.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.dtos.zoomsessionbook.ConfirmZoomSessionFromStudentRequest;
import com.guidebook.GuideBook.dtos.zoomsessionbook.GetZoomSessionFormDetailsResponse;
import com.guidebook.GuideBook.dtos.zoomsessionbook.ZoomSessionConfirmationRequest;
import com.guidebook.GuideBook.util.EncryptionUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZoomSessionBookService { //HANDLES FROM CONFIRMATION PART FROM THE STUDENT
    @Value("${websitedomainname}")
    private String websiteDomainName;
    private final EmailServiceImpl emailServiceImpl;
    private final ZoomSessionFormRepository zoomSessionFormRepository;
    private StudentRepository studentRepository;
    @Autowired
    public ZoomSessionBookService(ZoomSessionFormRepository zoomSessionFormRepository,
                                  EmailServiceImpl emailServiceImpl,
                                  StudentRepository studentRepository) {
        this.zoomSessionFormRepository = zoomSessionFormRepository;
        this.emailServiceImpl = emailServiceImpl;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void handleZoomSessionFormSuccess(ZoomSessionConfirmationRequest request) {
        // Retrieve the form details from the database
        ZoomSessionForm form = zoomSessionFormRepository.findByZoomSessionFormId(request.getZoomSessionFormId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid form ID"));
        //THROW CUSTOM FORM NOT FOUND EXCEPTION HERE

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
    @Transactional
    private String prepareEmailContent(ZoomSessionForm form) {//THROWS AN EXCEPTION RELATED TO ENCPYPTED LINK
        String link = null;
        try {
            String encryptedFormId = EncryptionUtil.encrypt(form.getZoomSessionFormId());
            link = websiteDomainName + "/schedule-zoom-session/" + encryptedFormId;
        } catch (Exception e) {
            e.printStackTrace();//THROWS AN EXCEPTION RELATED TO ENCPYPTED LINK
        }
        // Prepare the email content with form details
        StringBuilder content = new StringBuilder();
        content.append("Dear Student,\n\n");
        content.append("New request for Zoom session. Here are the details:\n\n");
        content.append("Full Name: ").append(form.getClientFirstName()).append(" ");
        content.append(form.getClientMiddleName()).append(" ");
        content.append(form.getClientLastName()).append("\n");
        content.append("Email: ").append(form.getClientEmail()).append("\n");
        content.append("Phone Number: ").append(form.getClientPhoneNumber()).append("\n");
        content.append("Age: ").append(form.getClientAge()).append("\n");
        content.append("College: ").append(form.getClientCollege()).append("\n");
        content.append("Proof Document Link: ").append(form.getClientProofDocLink()).append("\n");
        content.append("CONFIRM THE SESSION ONLY IF CLIENT ID, FEE RECEIPT IS VALID COLLEGE ID, RECEIPT  OTHERWISE STRICT ACTIONS ARE TAKEN BY THE COMPANY.");
        content.append("\nPlease confirm your availability for the Zoom session by clicking on this link: " + link + "\n\n");

        content.append("Best regards,\n");
        content.append("GuideBookX Team");
        return content.toString();
    }

    public GetZoomSessionFormDetailsResponse getZoomSessionVerifiedFormDetails(String formId) {
        Optional<ZoomSessionForm> checkForm = zoomSessionFormRepository.findByZoomSessionFormId(formId);
        if(checkForm.isPresent()){
            ZoomSessionForm form = checkForm.get();
            if(form.getIsVerified() == 1){
                return GetZoomSessionFormDetailsResponse.builder()
                        .clientFirstName(form.getClientFirstName())
                        .clientMiddleName(form.getClientMiddleName())
                        .clientLastName(form.getClientLastName())
                        .clientCollege(form.getClientCollege())
                        .clientAge(form.getClientAge())
                        .clientEmail(form.getClientEmail())
                        .clientPhoneNumber(form.getClientPhoneNumber())
                        .clientProofDocLink(form.getClientProofDocLink())
                        .isVerified(form.getIsVerified())
//                        .clientFeedbackFormLink() //COMPLETE THIS
                        .createdOn(form.getCreatedOn())
                        .build();
            } else { //RARE CASE
                //SEND A DTO TO THE FRONTEND SAYING THAT THE FORM IS NOT VERIFIED
                //PLEASE DISCARD SCHEDULING THE SESSION
                //IF SCHEDULED EVEN AFTER WARNING, YOUR ACCOUNT WILL BE REMOVED AND BLOCK FROM THE PLATFORM
                return GetZoomSessionFormDetailsResponse.builder()
                        .isVerified(form.getIsVerified())
                        .createdOn(form.getCreatedOn())
                        .build();
            }
        } else {
            //THROW CUSTOM FORM NOT FOUND EXCEPTION
            throw new IllegalArgumentException();
        }
    }

    public void confirmZoomSessionFromStudent(ConfirmZoomSessionFromStudentRequest request) {
        // Find student details using formId or other means
        Student student = studentRepository.findByStudentWorkEmail(request.getStudentWorkEmail());
        String studentName = student.getStudentName();

        Optional<ZoomSessionForm> optionalForm = zoomSessionFormRepository.findByZoomSessionFormId(request.getZoomSessionFormId());
        if (!optionalForm.isPresent()) {
            throw new RuntimeException("Zoom session form not found");
            //THROW CUSTOM FORM NOT FOUND EXCEPTION HERE
        }

        // Fetch client details from formDetails
        ZoomSessionForm form = optionalForm.get();
        String clientName = form.getClientFirstName() + " " + form.getClientLastName();
        String clientEmail = form.getClientEmail();

        String subject;
        String text;

        // Email to the client
        if (request.getIsAvailable() == 0) {
            subject = "Zoom Session Unavailability Notification";
            text = String.format("Dear %s,\n\n%s is not available for the meeting anytime soon.\nStudent has left a message for you:\n%s",
                    clientName, studentName, request.getStudentMessageToClient());
        } else {
            subject = "Zoom Session Confirmation";
            text = String.format("Dear %s,\n\n%s has accepted your Zoom session request and has scheduled the meeting.\n\nFollowing are the details of the meeting:\n\n1. Time: %s\n2. Meeting ID: %s\n3. Passcode: %s\n4. Meeting Link: %s\n\nKindly do not share these details with anyone. Only the email with which you have registered is permitted to be in the meeting otherwise the meeting will be cancelled.\n\nIf you have any issues regarding the email, please send an email to us at help.guidebookx@gmail.com",
                    clientName, studentName, request.getZoomSessionTime(), request.getZoomSessionMeetingId(), request.getZoomSessionPasscode(), request.getZoomSessionMeetingLink());
        }
        emailServiceImpl.sendSimpleMessage(clientEmail, subject, text);

        // Email to the student
        String studentSubject = "Zoom Session Confirmation";
        String studentText = String.format("Your Zoom meeting with %s is scheduled as per the following details:\n\nClient Name: %s\nClient Email: %s\nClient Phone Number: %s\nClient Age: %s\nClient College: %s\nProof Document: %s\n\nYou are helping someone in need. Keep up the great work!",
                clientName, clientName, clientEmail, form.getClientPhoneNumber(), form.getClientAge(), form.getClientCollege(), form.getClientProofDocLink());

        emailServiceImpl.sendSimpleMessage(student.getStudentWorkEmail(), studentSubject, studentText);

    }

//    private String clientFeedbackFormLinkGenerator(String studentWorkEmail, String zoomSessionFormId){
//
//    }
}
