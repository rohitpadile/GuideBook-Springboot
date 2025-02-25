package com.guidebook.GuideBook.ADMIN.Services.zoomsessionbook;

import com.guidebook.GuideBook.ADMIN.Models.BookingRestriction;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionForm;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Services.BookingRestrictionService;
import com.guidebook.GuideBook.ADMIN.Services.ZoomSessionTransactionService;
import com.guidebook.GuideBook.ADMIN.dtos.CancellationStatusZoomSessionViaTransactionIdRequest;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook.*;
import com.guidebook.GuideBook.ADMIN.enums.ZoomSessionBookStatus;
import com.guidebook.GuideBook.ADMIN.exceptions.BookingBlockedException;
import com.guidebook.GuideBook.ADMIN.exceptions.EncryptionFailedException;
import com.guidebook.GuideBook.ADMIN.exceptions.ZoomSessionNotFoundException;
import com.guidebook.GuideBook.ADMIN.util.EncryptionUtil;
import com.guidebook.GuideBook.ADMIN.util.EncryptionUtilForFeedbackForm;
import com.guidebook.GuideBook.ADMIN.util.EncryptionUtilForZoomSessionCancel;
import com.guidebook.GuideBook.ADMIN.Models.Student;
//import com.guidebook.GuideBook.Models.ZoomSessionTransactionFree;
import com.guidebook.GuideBook.ADMIN.Repository.StudentRepository;
import com.guidebook.GuideBook.ADMIN.Repository.ZoomSessionFormRepository;
import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
    public void handleZoomSessionFormSuccess(ZoomSessionConfirmationRequest request)
            throws ZoomSessionNotFoundException,
            EncryptionFailedException,
            BookingBlockedException {
        // Retrieve the form details from the database


        Optional<ZoomSessionForm> checkForm = zoomSessionFormRepository.findByZoomSessionFormId(request.getZoomSessionFormId());
        if(!checkForm.isPresent()){
            throw new ZoomSessionNotFoundException("Zoom session not found at handleZoomSessionFormSuccess() method");
        }
        ZoomSessionForm form = checkForm.get();

        //DONT LET A MENTOR BOOK A SESSION WITH HIMSELF
        if(request.getStudentWorkEmail().equalsIgnoreCase(form.getUserEmail())){
            throw new BookingBlockedException("Mentor cannot book a session with himself");
        }
        //Dont let him click on Book session multiple times - spamming email for student
        Optional<BookingRestriction> restriction = bookingRestrictionService.findByClientEmail(form.getUserEmail());
        if (restriction.isPresent()) {
            log.info("User Email just went into restriction: {}", form.getClientEmail());
            return;
        }
        // Prepare and send the email content to the student
        String studentEmailContent = prepareEmailContentForStudent(form, request.getStudentWorkEmail());
        emailServiceImpl.sendSimpleMessage(request.getStudentWorkEmail(),
                "Zoom Session Request from " + form.getClientFirstName() + " " + form.getClientLastName(),
                studentEmailContent);

        // Prepare and send the email content to the client
        String clientEmailContent = prepareEmailContentForClient(form, request.getStudentWorkEmail() );
        emailServiceImpl.sendSimpleMessage(form.getClientEmail(),
                "Zoom Session Request Confirmation",
                clientEmailContent);

        form.setIsVerified(1); //FORM IS MARKED AS VERIFIED SO NOT TO DELETE IT VIA SCHEDULED TASKS
        form.setZoomSessionBookStatus(ZoomSessionBookStatus.PENDING.toString());

        //SAVE THE FORM'S CLIENT EMAIL IN OUR BookingRestriction TABLE WHICH WILL DELETE IT AFTER X HOURS
        // Save the form's client email in the new table
        BookingRestriction newRestriction = new BookingRestriction();
        newRestriction.setClientEmail(form.getUserEmail());
        newRestriction.setCreatedOn(new Date()); // This will be set automatically by @CreationTimestamp
        bookingRestrictionService.save(newRestriction);

        zoomSessionFormRepository.save(form);
    }
    @Transactional
    private String prepareEmailContentForStudent(ZoomSessionForm form, String studentWorkEmail)
    throws EncryptionFailedException {
        String Pagelink = null;
        try {
            String encryptedFormId = EncryptionUtil.encrypt(form.getZoomSessionFormId());
            String encryptedStudentWorkEmail = EncryptionUtil.encrypt(studentWorkEmail);
            String encryptedData = encryptedFormId + "." + encryptedStudentWorkEmail;
            String encodedEncryptedData = URLEncoder.encode(encryptedData, StandardCharsets.UTF_8.toString());
            Pagelink = websiteDomainName + "/schedule-zoom-session/" + encodedEncryptedData;
        } catch (Exception e) {
//            e.printStackTrace(); // throw custom encryption exception
            throw new EncryptionFailedException("Encryption for studentWorkEmail and form id failed" + e.getMessage());
        }
        // Prepare the email content with form details
        StringBuilder content = new StringBuilder();
        content.append("Dear Student,\n\n");
        content.append("New request for Zoom session. Here are the details:\n\n");
        content.append("Full Name: ").append(form.getClientFirstName()).append(" ");
        if(!(form.getClientMiddleName()==null) && !form.getClientMiddleName().isEmpty()){
            content.append(form.getClientMiddleName()).append(" ");
        }
        content.append(form.getClientLastName()).append("\n");
        content.append("Email: ").append(form.getClientEmail()).append("\n");
        content.append("Phone Number: ").append(form.getClientPhoneNumber()).append("\n");
        content.append("Age: ").append(form.getClientAge()).append("\n");
        content.append("College: ").append(form.getClientCollege()).append("\n");
        content.append("Proof Document Link: \n").append(form.getClientProofDocLink()).append("\n");
        content.append("\nImportant Note: Confirm the session only if the " +
                "client documents are valid. \nPlease read terms and conditions of the company before proceeding.");
        content.append("\n\nPlease confirm your availability for the Zoom session by clicking on this link: \n" + Pagelink);

        content.append("\n\nBest regards,\n");
        content.append("GuideBookX Team");
        return content.toString();
    }
    @Transactional
    private String prepareEmailContentForClient(ZoomSessionForm form, String studentWorkEmail)
            throws EncryptionFailedException
    {
        String cancelPageLink = null;
        try {
            String encryptedFormId = EncryptionUtilForZoomSessionCancel.encrypt(form.getZoomSessionFormId(),  studentWorkEmail);
            cancelPageLink = websiteDomainName + "/cancel-zoom-session/" + encryptedFormId;
        } catch (Exception e) {
            throw new EncryptionFailedException("Encryption for form id failed: " + e.getMessage());
        }

        StringBuilder content = new StringBuilder();
        content.append("Dear ").append(form.getClientFirstName()).append(" ").append(form.getClientLastName()).append(",\n\n");
        content.append("Thank you for your Zoom session request. Here are the details you submitted:\n\n");
        content.append("Full Name: ").append(form.getClientFirstName()).append(" ");
        if (form.getClientMiddleName() != null && !form.getClientMiddleName().isEmpty()) {
            content.append(form.getClientMiddleName()).append(" ");
        }
        content.append(form.getClientLastName()).append("\n");
        content.append("Email: ").append(form.getClientEmail()).append("\n");
        content.append("Phone Number: ").append(form.getClientPhoneNumber()).append("\n");
        content.append("Age: ").append(form.getClientAge()).append("\n");
        content.append("College: ").append(form.getClientCollege()).append("\n");
        content.append("Session Duration: ").append(form.getZoomSessionDurationInMin()).append("\n");
        content.append("Goals through the sessions: ").append(form.getZoomSessionClientGoals()).append("\n");
        content.append("Expectations from the session: ").append(form.getZoomSessionClientExpectations()).append("\n\n");
        content.append("Proof Document Link: \n").append(form.getClientProofDocLink()).append("\n\n");
        content.append("If you wish to cancel the session, please click on the link below:\n").append(cancelPageLink);
        content.append("\n\nBest regards,\n");
        content.append("GuideBookX Team");
        return content.toString();
    }

    public GetZoomSessionFormDetailsResponse getZoomSessionVerifiedFormDetails(String formId)
            throws ZoomSessionNotFoundException
    {
        Optional<ZoomSessionForm> checkForm = zoomSessionFormRepository.findByZoomSessionFormId(formId);
        if(!checkForm.isPresent()){
            throw new ZoomSessionNotFoundException("Zoom session form not found at getZoomSessionVerifiedFormDetails() method");

        }
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
                    .zoomSessionDurationInMin(form.getZoomSessionDurationInMin())
                    .zoomSessionClientGoals(form.getZoomSessionClientGoals())
                    .zoomSessionClientExpectations(form.getZoomSessionClientExpectations())
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

    }
    @Transactional
    public void confirmZoomSessionFromStudent(
            ConfirmZoomSessionFromStudentRequest request)
            throws ZoomSessionNotFoundException, EncryptionFailedException {
        // Find student details using formId or other means
        Student student = studentRepository.findByStudentWorkEmailIgnoreCase(request.getStudentWorkEmail());
        String studentName = student.getStudentName();

        Optional<ZoomSessionForm> optionalForm = zoomSessionFormRepository.findByZoomSessionFormId(request.getZoomSessionFormId());
        if (!optionalForm.isPresent()) {
            throw new ZoomSessionNotFoundException("Zoom session form not found at confirmZoomSessionFromStudent() method");
            //THROW CUSTOM FORM NOT FOUND EXCEPTION HERE
        }
        // Fetch client details from formDetails
        ZoomSessionForm form = optionalForm.get();
        String clientName = form.getClientFirstName() + " " + form.getClientLastName();
        String clientEmail = form.getClientEmail();

        //MAKE A TRANSACTION HERE - FILL THE studentWorkEmail,
        // fk_formId, feePaid( set a application.property for fee, right now = 0),
//        ZoomSessionTransaction transaction =
//                zoomSessionTransactionService.createFreeTransaction(student,form);
        ZoomSessionTransaction transaction =
                zoomSessionTransactionService.createPaidTransaction(student,form);

        String paymentPageLink = websiteDomainName + "/confirmZoomSession/" + transaction.getZoomSessionTransactionId();
        //we are secured with JWT, so no worries, no encryption needed.


        String feedbackPageLink;
        try {
            String transactionId = transaction.getZoomSessionTransactionId();
            String encodedId = EncryptionUtilForFeedbackForm.encode(transactionId, studentName); // Custom encode
            feedbackPageLink = websiteDomainName + "/feedback-zoom-session/" + URLEncoder.encode(encodedId, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            log.error("Error at encoding transaction id into feedback link: {}", e.getMessage());
            throw new EncryptionFailedException("Error Encrypting feedback link at confirmZoomSessionFromStudent() method");
        }

        String clientSubject;
        String clientText;
        String studentSubject;
        String studentText;
        // Email to the client

        if (request.getIsAvailable() == 0) { //Session cancelled from student
            clientSubject = "Zoom Session Unavailability Notification";
            clientText = String.format("Dear %s,\n\n%s is not available for the meeting anytime soon.\nStudent has left a message for you:\n\nMessage: %s" +
                            "\n\nBest regards,\n" +
                            "GuidebookX Team",
                    clientName, studentName, request.getStudentMessageToClient());

            studentSubject = "Zoom Session Cancellation";
            studentText = String.format("Your zoom session with %s is Cancelled." +
                            "\n\nFollowing were the client details\n\nClient Name: %s" +
                            "\nClient Email: %s\nClient Phone Number: %s\nClient Age: %s" +
                            "\nClient College: %s\nProof Document: \n%s" +
                            "\n\nIf you have any issues please send " +
                            "an email to us at mentorhelpguidebookx@gmail.com" +
                            "\n\nBest regards,\n" +
                            "GuidebookX Team",
                    clientName, clientName, clientEmail, form.getClientPhoneNumber(), form.getClientAge(), form.getClientCollege(), form.getClientProofDocLink());

            form.setZoomSessionBookStatus(ZoomSessionBookStatus.CANCELLED.toString());
            zoomSessionFormRepository.save(form);
        } else { //Session Booked from student
            clientSubject = "Zoom Session Confirmation";
            ////////////////////SEND THE PAYMENT LINK HERE IN THIS MAIL TO CLIENT
            clientText = String.format("Dear %s,\n\n%s has accepted your Zoom session request and has" +
                            " scheduled the meeting.\n\nFollowing are the details of the meeting:\n\n" +
                            "1. Time: %s\n" +
                            "2. Meeting ID: %s\n" +
                            "3. Passcode: %s\n" +
                            "4. Meeting Link: \n%s\n\n" +
                    //PLACE THE LINK IN THE BELOW LINE FOR PAYMENT PAGE
                            "To confirm the session from your side, please proceed to payment page by clicking on this link: %s\n\n" +
                            "At the end of the session, please give the feedback, it takes a minute\n" +
                            "Important Note: Fill the form, then only the session will be counted in " +
                            "your account and you can win PRIZES in future." +
                            "\nThank you for your co-operation. Have a great session\n\n" +
                            "Feedback link: %s\n\n" +

                            "NOTE: Kindly do not share these details with anyone. Only the email with which " +
                            "you have registered is permitted to be in the meeting otherwise the meeting " +
                            "will be cancelled.\n\nIf you have any issues regarding the email, please send " +
                            "an email to us at helpguidebookx@gmail.com" +
                            "\n\nBest regards,\n" +
                            "GuidebookX Team",
                    clientName,
                    studentName,
                    convertTo12HourFormat(request.getZoomSessionTime()),
                    request.getZoomSessionMeetingId(),
                    request.getZoomSessionPasscode(),
                    request.getZoomSessionMeetingLink(),
                    paymentPageLink,
                    feedbackPageLink);

            studentSubject = "Zoom Session Confirmation";
            studentText = String.format("Your Zoom meeting with %s is scheduled as per your given details:" +
                            "\n\nClient Name: %s\nClient Email: %s\nClient Phone Number: %s\nClient Age: %s" +
                            "\nClient College: %s" +
                            "\nSession Duration: %s" +
                            "\nClient goals from the session: %s" +
                            "\nClient expectations from the session: %s" +
                            "\n\nProof Document: \n%s\n\n1. Time: %s\n2. Meeting ID: %s\n" +
                            "3. Passcode: %s\n4. Meeting Link: \n%s\n\n" +
                            "We will email you once the transaction is completed & the session is confirmed from the client." +
//                            "You are helping someone in need. Keep up the great work and have a great session!" +
                    //PUT THE ABOVE LINE IN THE CONFIRMATION EMAIL
                            "\n\nBest regards,\n" +
                            "GuidebookX Team",
                    clientName,
                    clientName,
                    clientEmail,
                    form.getClientPhoneNumber(),
                    form.getClientAge(),
                    form.getClientCollege(),
                    form.getZoomSessionDurationInMin(),
                    form.getZoomSessionClientGoals(),
                    form.getZoomSessionClientExpectations(),
                    form.getClientProofDocLink(),
                    convertTo12HourFormat(request.getZoomSessionTime()),
                    request.getZoomSessionMeetingId(),
                    request.getZoomSessionPasscode(),
                    request.getZoomSessionMeetingLink());

            form.setZoomSessionBookStatus(ZoomSessionBookStatus.SCHEDULED.toString()); //keep it pending till payment is completed
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

    public void cancelZoomSessionFromClient(CancelZoomSessionFromClientRequest request)
            throws ZoomSessionNotFoundException
    {
        Optional<ZoomSessionForm> checkForm = zoomSessionFormRepository.findByZoomSessionFormId(request.getZoomSessionFormId());
        if(!checkForm.isPresent()){
            throw new ZoomSessionNotFoundException("Zoom session form not found at cancelZoomSessionFromClient() method");
        }
        ZoomSessionForm form = checkForm.get();
        form.setZoomSessionBookStatus(ZoomSessionBookStatus.CANCELLED.toString());
        zoomSessionFormRepository.save(form);

        // Prepare and send the email content to the student
        String studentEmailContent = prepareCancelEmailContentForStudent(form);
        emailServiceImpl.sendSimpleMessage(request.getStudentWorkEmail(),
                "Zoom Session Cancelled by Client",
                studentEmailContent);

        // Prepare and send the email content to the client
        String clientEmailContent = prepareCancelEmailContentForClient(form, request.getStudentWorkEmail());
        emailServiceImpl.sendSimpleMessage(form.getClientEmail(),
                "Zoom Session Cancellation Confirmation",
                clientEmailContent);
    }

    private String prepareCancelEmailContentForStudent(ZoomSessionForm form) {
        StringBuilder content = new StringBuilder();
        content.append("Dear Student,\n\n");
        content.append("We regret to inform you that the Zoom session with ")
                .append(form.getClientFirstName()).append(" ")
                .append(form.getClientLastName()).append(" has been cancelled by the client.\n\n");
        content.append("Client Details:\n");
        content.append("Full Name: ").append(form.getClientFirstName()).append(" ");
        if(form.getClientMiddleName() != null && !form.getClientMiddleName().isEmpty()){
            content.append(form.getClientMiddleName()).append(" ");
        }
        content.append(form.getClientLastName()).append("\n");
        content.append("Email: ").append(form.getClientEmail()).append("\n");
        content.append("Phone Number: ").append(form.getClientPhoneNumber()).append("\n");
        content.append("Age: ").append(form.getClientAge()).append("\n");
        content.append("College: ").append(form.getClientCollege()).append("\n");
        content.append("Proof Document Link: \n").append(form.getClientProofDocLink()).append("\n");
        content.append("\nWe apologize for any inconvenience caused.\n\n");
        content.append("Best regards,\n");
        content.append("GuideBookX Team");
        return content.toString();
    }

    private String prepareCancelEmailContentForClient(ZoomSessionForm form, String studentWorkEmail) {
        StringBuilder content = new StringBuilder();
        content.append("Dear ").append(form.getClientFirstName()).append(" ").append(form.getClientLastName()).append(",\n\n");
        content.append("Your Zoom session with "+
                studentRepository.findByStudentWorkEmail(studentWorkEmail).getStudentName() + " has been successfully cancelled.\n\n");
        content.append("Session Details:\n");
        content.append("Full Name: ").append(form.getClientFirstName()).append(" ");
        if(form.getClientMiddleName() != null && !form.getClientMiddleName().isEmpty()){
            content.append(form.getClientMiddleName()).append(" ");
        }
        content.append(form.getClientLastName()).append("\n");
        content.append("Email: ").append(form.getClientEmail()).append("\n");
        content.append("Phone Number: ").append(form.getClientPhoneNumber()).append("\n");
        content.append("Age: ").append(form.getClientAge()).append("\n");
        content.append("College: ").append(form.getClientCollege()).append("\n");
        content.append("Session Duration: ").append(form.getZoomSessionDurationInMin()).append("\n");
        content.append("Goals through the sessions: ").append(form.getZoomSessionClientGoals()).append("\n");
        content.append("Expectations from the session: ").append(form.getZoomSessionClientExpectations()).append("\n\n");
        content.append("Proof Document Link: \n").append(form.getClientProofDocLink()).append("\n");
        content.append("\nWe apologize for any inconvenience caused.\n\n");
        content.append("If you have any issues, please send an email to us at helpguidebookx@gmail.com");
        content.append("\n\nBest regards,\n");
        content.append("GuideBookX Team");
        return content.toString();
    }

    public CancellationStatusZoomSessionResponse cancelZoomSessionCheckStatus(CancellationStatusZoomSessionRequest request)
            throws ZoomSessionNotFoundException
    {
        Optional<ZoomSessionForm> checkForm = zoomSessionFormRepository.findByZoomSessionFormId(request.getFormId());
        if(!checkForm.isPresent()){
            throw new ZoomSessionNotFoundException("Zoom session form not found at cancelZoomSessionStatus() method");
        }
        ZoomSessionForm form = checkForm.get();
        CancellationStatusZoomSessionResponse response = new CancellationStatusZoomSessionResponse();


        //CHANGE HOW TRANSACTION IS TREATED HERE - DONE !
        if(
                form.getZoomSessionBookStatus().equalsIgnoreCase(ZoomSessionBookStatus.CANCELLED.toString())

        ){
            log.info("Cancellation status is 1");
            response.setStatus(1);
        }else if(
                form.getZoomSessionBookStatus().equalsIgnoreCase(ZoomSessionBookStatus.BOOKED.toString())
        ){
            log.info("Booked status is 2");
            response.setStatus(2);
        } else {
            log.info("Pending status is 0");
            response.setStatus(0);
        }
        return response;
    }

    public CancellationStatusZoomSessionResponse cancelZoomSessionCheckStatusViaTransactionId(CancellationStatusZoomSessionViaTransactionIdRequest request)
            throws ZoomSessionNotFoundException
    {
        ZoomSessionForm form = zoomSessionTransactionService.getZoomSessionTransactionById(request.getZoomSessionTransactionId()).getZoomSessionForm();
        CancellationStatusZoomSessionResponse response = new CancellationStatusZoomSessionResponse();
        //CHANGE HOW TRANSACTION IS TREATED HERE - DONE !
        if(
                form.getZoomSessionBookStatus().equalsIgnoreCase(ZoomSessionBookStatus.CANCELLED.toString())
                ||
                form.getZoomSessionBookStatus().equalsIgnoreCase(ZoomSessionBookStatus.BOOKED.toString())

        ){
            log.info("Cancellation & booked status is 1");
            response.setStatus(1); //disable payment
        } else {
            log.info("Pending status is 0 & status is {}", form.getZoomSessionBookStatus());
            response.setStatus(0);
        }
        return response;
    }
}
