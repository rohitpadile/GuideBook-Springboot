package com.guidebook.GuideBook.ADMIN.Services.zoomsessionbook;

import com.guidebook.GuideBook.ADMIN.Models.BookingRestriction;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionForm;
import com.guidebook.GuideBook.ADMIN.Services.BookingRestrictionService;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionform.ZoomSessionFormMessageResponse;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionform.ZoomSessionOTPResendRequest;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionform.ZoomSessionOTPVerifyRequest;
import com.guidebook.GuideBook.ADMIN.Repository.ZoomSessionFormRepository;
import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionform.ZoomSessionFormRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class ZoomSessionFormService {
    private final EmailServiceImpl emailServiceImpl;
    private final ZoomSessionFormRepository zoomSessionFormRepository;
    private final BookingRestrictionService bookingRestrictionService;
    @Autowired
    public ZoomSessionFormService(ZoomSessionFormRepository zoomSessionFormRepository,
                                  EmailServiceImpl emailServiceImpl,
                                  BookingRestrictionService bookingRestrictionService) {
        this.zoomSessionFormRepository = zoomSessionFormRepository;
        this.emailServiceImpl = emailServiceImpl;
        this.bookingRestrictionService = bookingRestrictionService;
    }
    @Transactional
    public ZoomSessionFormMessageResponse submitForm(ZoomSessionFormRequest formDTO, String userEmail) {

        //DONT LET THE SAME CLIENT EMAIL BOOK ANOTHER SESSION WITHIN X HOURS (SPECIFIED IN APPLICATION PROPERTIES)
        Optional<BookingRestriction> restriction = bookingRestrictionService.findByClientEmail(formDTO.getClientEmail());

        if (restriction.isPresent()) {
            ZoomSessionFormMessageResponse response = getZoomSessionFormMessageResponse();
            log.error("Response is {}", response);
            return response;
        }

        ZoomSessionForm form = ZoomSessionForm.builder()
                .clientFirstName(formDTO.getClientFirstName())
                .clientMiddleName(formDTO.getClientMiddleName())
                .clientLastName(formDTO.getClientLastName())
                .clientEmail(formDTO.getClientEmail())
                .clientPhoneNumber(formDTO.getClientPhoneNumber())
                .clientAge(formDTO.getClientAge())
                .clientCollege(formDTO.getClientCollege())
                .clientProofDocLink(formDTO.getClientProofDocLink())
                .clientOtpAttempts(0) // Initialize OTP attempts
                .isVerified(0)
                .userEmail(userEmail)
                .zoomSessionDurationInMin(formDTO.getZoomSessionDurationInMin())//duration
                .zoomSessionClientGoals(formDTO.getZoomSessionClientGoals())//goals
                .zoomSessionClientExpectations(formDTO.getZoomSessionClientExpectations())//expectations
                .build();

        // Generate OTP and set expiration
        String otp = generateOTP();
        form.setClientOTP(otp);
        form.setClientOTPExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 minutes from now

        // Send OTP email
        String subject = "GuidebookX: OTP(One Time Password) for Verifying Zoom session form email";
        String text = "Dear " + formDTO.getClientFirstName() + ",\n\n" +
                "Welcome to GuidebookX! Your OTP for verification is: " + otp + ".\n\n" +
                "This OTP is valid for the next 5 minutes.\n\n" +
                "Thank you for choosing GuideBookX. We are dedicated to providing you with the best experience.\n\n" +
                "Best Regards,\n" +
                "GuideBookX Team";
        emailServiceImpl.sendSimpleMessage(formDTO.getClientEmail(), subject, text);

        // Save the form and return the response message
        ZoomSessionForm savedForm = zoomSessionFormRepository.save(form);

        ZoomSessionFormMessageResponse response = new ZoomSessionFormMessageResponse();
        response.setZoomSessionFormMessage("OTP has been sent to your email.");
        response.setZoomSessionFormMessageCode(1); //code for sending email
        response.setZoomSessionFormId(savedForm.getZoomSessionFormId());
        log.error("Response is {}", response);
        return response;
    }

    private ZoomSessionFormMessageResponse getZoomSessionFormMessageResponse() {
        ZoomSessionFormMessageResponse response = new ZoomSessionFormMessageResponse();
        response.setZoomSessionFormMessage(
                String.format(
                        "Sorry, as per company's terms and conditions, you cannot book more than 1 session within %d hour(s) of booking a session",
                        bookingRestrictionService.getTimeBeforeBookSessionNotAllowed()));
        response.setZoomSessionFormMessageCode(0); // Code for redirecting him to student profile page
        return response;
    }

    @Transactional
    public ZoomSessionFormMessageResponse verifyOTP(ZoomSessionOTPVerifyRequest zoomSessionOTPVerifyRequest) {
        String clientOTPForVerification = String.valueOf(zoomSessionOTPVerifyRequest.getClientOTP());
        ZoomSessionFormMessageResponse response = new ZoomSessionFormMessageResponse();
        Optional<ZoomSessionForm> formOptional =
                zoomSessionFormRepository.findByZoomSessionFormId(zoomSessionOTPVerifyRequest.getZoomSessionFormId());

        if (formOptional.isPresent()) {
            ZoomSessionForm form = formOptional.get();
            Date now = new Date();

            if (form.getClientOtpAttempts() >= 3) {
                // Max attempts reached
                response.setZoomSessionFormMessage("3 wrong attempts to the OTP has cancelled the form. Redirecting to student profile page.");
                response.setZoomSessionFormMessageCode(0); //code to redirect to student profile page
                zoomSessionFormRepository.delete(form);
                return response;
            }

            if (form.getClientOTPExpiration().before(now)) {
                // OTP is expired
                response.setZoomSessionFormMessage("Out of OTP time: Redirecting to student profile page.");
                response.setZoomSessionFormMessageCode(0);
                zoomSessionFormRepository.delete(form);
                return response;
            }

            if (form.getClientOTP().equals(clientOTPForVerification)) {
                // OTP is valid
                response.setZoomSessionFormMessage("OTP has been verified.");
                response.setZoomSessionFormMessageCode(2); //OTP VERIFICATION SUCCESS CODE
//                form.setIsVerified(1);
//                DO THIS ONLY WHEN WE TELL CLIENT, THAT WE ARE CONTACTING STUDENT ABOUT THE CONFIRMATION
                return response;
            } else {
                // OTP is invalid
                form.setClientOtpAttempts(form.getClientOtpAttempts() + 1);
                zoomSessionFormRepository.save(form); // Update the attempts count

                if(form.getClientOtpAttempts() == 3){
                    zoomSessionFormRepository.delete(form);
                    response.setZoomSessionFormMessage("3 wrong attempts to the OTP has cancelled the form. Redirecting to student profile page.");
                    response.setZoomSessionFormMessageCode(0); //code to redirect to student profile page
                } else {
                    response.setZoomSessionFormMessage("Wrong OTP: Attempts remaining: " + (3 - form.getClientOtpAttempts()));
                    response.setZoomSessionFormMessageCode(-1); //code for wrong attempt of otp
                }

                return response;
            }
        } else {
            response.setZoomSessionFormMessage("No form found for the provided email.");
            response.setZoomSessionFormMessageCode(0); //redirect to student profile page
        }

        return response;
    }
    @Transactional
    public ZoomSessionFormMessageResponse resendOTP(ZoomSessionOTPResendRequest request) {
        ZoomSessionFormMessageResponse response = new ZoomSessionFormMessageResponse();
        Optional<ZoomSessionForm> formOptional = zoomSessionFormRepository.findByZoomSessionFormId(request.getZoomSessionFormId());

//IMPORTANT NOTE:-

        //TWO FORMS WITH SAME EMAIL CAN APPEAR WHEN SENT OTP BUTTON IS CLICKED TWICE
        //BUT ONLY THE LATEST ONE'S FORM ID WILL BE STORED AT FRONTEND STATE

        if (formOptional.isPresent()) {
            ZoomSessionForm form = formOptional.get();
            String newOtp = generateOTP();
            form.setClientOTP(newOtp);
            form.setClientOTPExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 minutes from new otp


            // Send new OTP via email
            String subject = "GuideBookX: OTP(One Time Password) for Verifying Zoom session form email";
            String text = "Dear " + form.getClientFirstName() + form.getClientLastName() + ",\n\n" +
                    "Welcome to GuideBookX! Your NEW OTP for verification is: " + newOtp + ".\n\n" +
                    "This OTP is valid for the next 5 minutes.\n\n" +
                    "Thank you for choosing GuideBook. We are dedicated to providing you with the best experience.\n\n" +
                    "Best Regards,\n" +
                    "GuideBookX Team";
            emailServiceImpl.sendSimpleMessage(form.getClientEmail(), subject, text);

            response.setZoomSessionFormMessage("New OTP has been sent to your email.");
            response.setZoomSessionFormMessageCode(1); //code for sending email
            response.setZoomSessionFormId(form.getZoomSessionFormId());
            zoomSessionFormRepository.save(form);
        } else {
            response.setZoomSessionFormMessage("No form found for the provided email.");
            response.setZoomSessionFormMessageCode(0); //redirect to student profile page
        }

        return response;
    }
    private String generateOTP() {
        // Implement OTP generation logic
        // For example, generate a 6-digit random number
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    public Optional<ZoomSessionForm> getZoomSessionFormById(String id){
        return zoomSessionFormRepository.findByZoomSessionFormId(id);
    }

    public ZoomSessionForm updateZoomSessionForm(ZoomSessionForm form){
        return zoomSessionFormRepository.save(form);
    }
}

