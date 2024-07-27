package com.guidebook.GuideBook.Services.zoomsessionbook;

import com.guidebook.GuideBook.Models.BookingRestriction;
import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Models.ZoomSessionForm;
//import com.guidebook.GuideBook.Models.ZoomSessionTransactionFree;
import com.guidebook.GuideBook.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.Repository.StudentRepository;
import com.guidebook.GuideBook.Repository.ZoomSessionFormRepository;
import com.guidebook.GuideBook.Repository.ZoomSessionTransactionRepository;
import com.guidebook.GuideBook.Services.BookingRestrictionService;
import com.guidebook.GuideBook.Services.ZoomSessionTransactionService;
import com.guidebook.GuideBook.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.dtos.zoomsessionbook.ConfirmZoomSessionFromStudentRequest;
import com.guidebook.GuideBook.dtos.zoomsessionbook.GetZoomSessionFormDetailsResponse;
import com.guidebook.GuideBook.dtos.zoomsessionbook.ZoomSessionConfirmationRequest;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionFormMessageResponse;
import com.guidebook.GuideBook.enums.ZoomSessionBookStatus;
import com.guidebook.GuideBook.util.EncryptionUtil;
import com.guidebook.GuideBook.util.EncryptionUtilForFeedbackForm;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class ZoomSessionBookService { //HANDLES FROM CONFIRMATION PART FROM THE STUDENT
    @Value("${websitedomainname}")
    private String websiteDomainName;
    private EmailServiceImpl emailServiceImpl;
    private ZoomSessionFormRepository zoomSessionFormRepository;
    private ZoomSessionTransactionService zoomSessionTransactionService;
    private StudentRepository studentRepository;
    private final BookingRestrictionService bookingRestrictionService;
    @Autowired
    public ZoomSessionBookService(ZoomSessionFormRepository zoomSessionFormRepository,
                                  EmailServiceImpl emailServiceImpl,
                                  StudentRepository studentRepository,
                                  ZoomSessionTransactionService zoomSessionTransactionService,
                                  BookingRestrictionService bookingRestrictionService) {
        this.zoomSessionFormRepository = zoomSessionFormRepository;
        this.emailServiceImpl = emailServiceImpl;
        this.studentRepository = studentRepository;
        this.zoomSessionTransactionService = zoomSessionTransactionService;
        this.bookingRestrictionService = bookingRestrictionService;
    }

    @Transactional
    public void handleZoomSessionFormSuccess(ZoomSessionConfirmationRequest request) {
        // Retrieve the form details from the database
        ZoomSessionForm form = zoomSessionFormRepository.findByZoomSessionFormId(request.getZoomSessionFormId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid form ID"));
        //THROW CUSTOM FORM NOT FOUND EXCEPTION HERE

        //Dont let him click on Book session multiple times - spamming email for student
        Optional<BookingRestriction> restriction = bookingRestrictionService.findByClientEmail(form.getClientEmail());
        if (restriction.isPresent()) {
            log.info("Client Email just went into restriction: {}", form.getClientEmail());
            return;
        }
        // Prepare the email content
        String emailContent = prepareEmailContent(form, request.getStudentWorkEmail());

        // Send the email to the student
        emailServiceImpl.sendSimpleMessage(request.getStudentWorkEmail(),
                "Zoom Session Request from " +
                        form.getClientFirstName() + " " +
                        form.getClientLastName(),
                emailContent);
        form.setIsVerified(1); //FORM IS MARKED AS VERIFIED SO NOT TO DELETE IT VIA SCHEDULED TASKS
        form.setZoomSessionBookStatus(ZoomSessionBookStatus.PENDING.toString());

        //SAVE THE FORM'S CLIENT EMAIL IN OUR BookingRestriction TABLE WHICH WILL DELETE IT AFTER X HOURS
        // Save the form's client email in the new table
        BookingRestriction newRestriction = new BookingRestriction();
        newRestriction.setClientEmail(form.getClientEmail());
        newRestriction.setCreatedOn(new Date()); // This will be set automatically by @CreationTimestamp
        bookingRestrictionService.save(newRestriction);

        zoomSessionFormRepository.save(form);
    }
    @Transactional
    private String prepareEmailContent(ZoomSessionForm form, String studentWorkEmail) {
        String Pagelink = null;
        try {
            String encryptedFormId = EncryptionUtil.encrypt(form.getZoomSessionFormId());
            String encryptedStudentWorkEmail = EncryptionUtil.encrypt(studentWorkEmail);
            String encryptedData = encryptedFormId + "." + encryptedStudentWorkEmail;
            String encodedEncryptedData = URLEncoder.encode(encryptedData, StandardCharsets.UTF_8.toString());
            Pagelink = websiteDomainName + "/schedule-zoom-session/" + encodedEncryptedData;
        } catch (Exception e) {
            e.printStackTrace(); // throw custom encryption exception
        }
        // Prepare the email content with form details
        StringBuilder content = new StringBuilder();
        content.append("Dear Student,\n\n");
        content.append("New request for Zoom session. Here are the details:\n\n");
        content.append("Full Name: \t").append(form.getClientFirstName()).append(" ");
        if(!(form.getClientMiddleName()==null) && !form.getClientMiddleName().isEmpty()){
            content.append(form.getClientMiddleName()).append(" ");
        }
        content.append(form.getClientLastName()).append("\n");
        content.append("Email: \t").append(form.getClientEmail()).append("\n");
        content.append("Phone Number: \t").append(form.getClientPhoneNumber()).append("\n");
        content.append("Age: \t").append(form.getClientAge()).append("\n");
        content.append("College: \t").append(form.getClientCollege()).append("\n");
        content.append("Proof Document Link: \n").append(form.getClientProofDocLink()).append("\n");
        content.append("\nImportant Note: Confirm the session only if the College Id, Fee Receipt " +
                "and Adhar Card match and are valid. Otherwise strict actions are to be taken" +
                " by the Company.");
        content.append("\n\nPlease confirm your availability for the Zoom session by clicking on this link: \n" + Pagelink);

        content.append("\n\nBest regards,\n");
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
                        .bookStatus(form.getZoomSessionBookStatus().toString())
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
//    @Transactional
    public void confirmZoomSessionFromStudent(
            ConfirmZoomSessionFromStudentRequest request)
    {
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

        //MAKE A TRANSACTION HERE - FILL THE studentWorkEmail,
        // fk_formId, feePaid( set a application.property for fee, right now = 0),
        ZoomSessionTransaction transaction =
                zoomSessionTransactionService.createFreeTransaction(student,form);

        //COMMENTED CODE FOR ENCRYPTION
        //CREATE A URL with domain name + /feedback-zoom-session/ + encrypted url(with transaction.getZoomSessionTransactionId() in it)
        String feedbackPageLink = "ERROR IN CREATING FEEDBACK FORM LINK: PLEASE CONTACT COMPANY VIA MAIL";
        try {
            String transactionId = transaction.getZoomSessionTransactionId();
            String encodedId = EncryptionUtilForFeedbackForm.encode(transactionId, studentName); // Custom encode
            feedbackPageLink = websiteDomainName + "/feedback-zoom-session/" + URLEncoder.encode(encodedId, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            log.error("Error at encoding transaction id into feedback link: {}", e.getMessage());
        }

        String clientSubject;
        String clientText;
        String studentSubject;
        String studentText;
        // Email to the client

        if (request.getIsAvailable() == 0) { //Session cancelled from student
            clientSubject = "Zoom Session Unavailability Notification";
            clientText = String.format("Dear %s,\n\n%s is not available for the meeting anytime soon.\nStudent has left a message for you:\n\nMessage: %s",
                    clientName, studentName, request.getStudentMessageToClient());

            studentSubject = "Zoom Session Cancellation";
            studentText = String.format("Your zoom session with %s is Cancelled." +
                            "\n\nFollowing are the client details\n\nClient Name: %s" +
                            "\nClient Email: %s\nClient Phone Number: %s\nClient Age: %s" +
                            "\nClient College: %s\nProof Document: \n%s" +
                            "\n\nBest regards,\n" +
                            "GuidebookX Team",
                    clientName, clientName, clientEmail, form.getClientPhoneNumber(), form.getClientAge(), form.getClientCollege(), form.getClientProofDocLink());

            form.setZoomSessionBookStatus(ZoomSessionBookStatus.CANCELLED.toString());
            zoomSessionFormRepository.save(form);
        } else { //Session Booked from student
            clientSubject = "Zoom Session Confirmation";
            clientText = String.format("Dear %s,\n\n%s has accepted your Zoom session request and has" +
                            " scheduled the meeting.\n\nFollowing are the details of the meeting:\n\n" +
                            "1. Time: %s\n" +
                            "2. Meeting ID: %s\n" +
                            "3. Passcode: %s\n" +
                            "4. Meeting Link: \n%s\n\n" +
                            "At the end of the session, please give the feedback.\n" +
                            "Important Note: Fill the form, then only the session will be counted in " +
                            "student's account and he can provide more such sessions in the future." +
                            "\nThank you for your co-operation. Have a great session\n\n" +
                            "Feedback link: %s\n\n" +

                            "NOTE: Kindly do not share these details with anyone. Only the email with which " +
                            "you have registered is permitted to be in the meeting otherwise the meeting " +
                            "will be cancelled.\n\nIf you have any issues regarding the email, please send " +
                            "an email to us at help.guidebookx@gmail.com",
                    clientName,
                    studentName,
                    convertTo12HourFormat(request.getZoomSessionTime()),
                    request.getZoomSessionMeetingId(),
                    request.getZoomSessionPasscode(),
                    request.getZoomSessionMeetingLink(),
                    feedbackPageLink);

            studentSubject = "Zoom Session Confirmation";
            studentText = String.format("Your Zoom meeting with %s is scheduled as per the following details:" +
                            "\n\nClient Name: %s\nClient Email: %s\nClient Phone Number: %s\nClient Age: %s" +
                            "\nClient College: %s\nProof Document: \n%s\n\n1. Time: %s\n2. Meeting ID: %s\n" +
                            "3. Passcode: %s\n4. Meeting Link: \n%s\n\n" +
                            "You are helping someone in need. Keep up the great work and have a great session!" +
                            "\n\nBest regards,\n" +
                            "GuidebookX Team",
                    clientName,
                    clientName,
                    clientEmail,
                    form.getClientPhoneNumber(),
                    form.getClientAge(),
                    form.getClientCollege(),
                    form.getClientProofDocLink(),
                    convertTo12HourFormat(request.getZoomSessionTime()),
                    request.getZoomSessionMeetingId(),
                    request.getZoomSessionPasscode(),
                    request.getZoomSessionMeetingLink());

            form.setZoomSessionBookStatus(ZoomSessionBookStatus.BOOKED.toString());
            zoomSessionFormRepository.save(form);
        }

        emailServiceImpl.sendSimpleMessage(clientEmail, clientSubject, clientText);
        emailServiceImpl.sendSimpleMessage(student.getStudentWorkEmail(), studentSubject, studentText);
    }

//    private String clientFeedbackFormLinkGenerator(String studentWorkEmail, String zoomSessionFormId){
//
//    }

    private static String convertTo12HourFormat(String dateTimeStr) {
        // Define the input and output formatters
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

        // Parse the input string to LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, inputFormatter);

        // Format the LocalDateTime to 12-hour format with AM/PM and day-month-year format
        return dateTime.format(outputFormatter);
    }
}
