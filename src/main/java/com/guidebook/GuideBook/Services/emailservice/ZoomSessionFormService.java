package com.guidebook.GuideBook.Services.emailservice;

import com.guidebook.GuideBook.Models.ZoomSessionForm;
import com.guidebook.GuideBook.Repository.ZoomSessionFormRepository;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionFormRequest;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionFormMessageResponse;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionOTPVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ZoomSessionFormService {
    private final EmailServiceImpl emailServiceImpl;
    private final ZoomSessionFormRepository zoomSessionFormRepository;
    @Autowired
    public ZoomSessionFormService(ZoomSessionFormRepository zoomSessionFormRepository,
                                  EmailServiceImpl emailServiceImpl) {
        this.zoomSessionFormRepository = zoomSessionFormRepository;
        this.emailServiceImpl = emailServiceImpl;
    }

    public ZoomSessionFormMessageResponse submitForm(ZoomSessionFormRequest formDTO) {
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
                .build();

        // Generate OTP and set expiration
        String otp = generateOTP();
        form.setClientOTP(otp);
        form.setClientOTPExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 minutes from now

        // Send OTP email
        String subject = "GuideBookX: OTP(One Time Password) for Verifying Zoom session form email";
        String text = "Dear " + formDTO.getClientFirstName() + ",\n\n" +
                "Welcome to GuideBook! Your OTP for verification is: " + otp + ".\n\n" +
                "This OTP is valid for the next 5 minutes.\n\n" +
                "Thank you for choosing GuideBook. We are dedicated to providing you with the best experience.\n\n" +
                "Best Regards,\n" +
                "GuideBook Team";
        emailServiceImpl.sendSimpleMessage(formDTO.getClientEmail(), subject, text);

        // Save the form and return the response message
        ZoomSessionForm savedForm = zoomSessionFormRepository.save(form);

        ZoomSessionFormMessageResponse response = new ZoomSessionFormMessageResponse();
        response.setZoomSessionFormMessage("OTP has been sent to your email.");
        response.setZoomSessionFormMessageCode(1); //code for sending email
        response.setZoomSessionFormId(savedForm.getZoomSessionFormId());
        return response;
    }

    public ZoomSessionFormMessageResponse verifyOTP(ZoomSessionOTPVerify zoomSessionOTPVerify) {
        String clientOTPForVerification = String.valueOf(zoomSessionOTPVerify.getClientOTP());
        ZoomSessionFormMessageResponse response = new ZoomSessionFormMessageResponse();
        Optional<ZoomSessionForm> formOptional =
                zoomSessionFormRepository.findByClientEmail(zoomSessionOTPVerify.getClientEmail());

        if (formOptional.isPresent()) {
            ZoomSessionForm form = formOptional.get();
            Date now = new Date();

            if (form.getClientOtpAttempts() >= 3) {
                // Max attempts reached
                response.setZoomSessionFormMessage("3 wrong attempts to the OTP has cancelled the form. Redirecting to student profile page.");
                response.setZoomSessionFormMessageCode(0); //code to redirect to student profile page
            }

            if (form.getClientOTPExpiration().before(now)) {
                // OTP is expired
                response.setZoomSessionFormMessage("Out of OTP time: Redirecting to student profile page.");
                response.setZoomSessionFormMessageCode(0);
            }

            if (form.getClientOTP().equals(clientOTPForVerification)) {
                // OTP is valid
                response.setZoomSessionFormMessage("OTP has been verified.");
                response.setZoomSessionFormMessageCode(2); //code for otp verification sucess
            } else {
                // OTP is invalid
                form.setClientOtpAttempts(form.getClientOtpAttempts() + 1);
                zoomSessionFormRepository.save(form); // Update the attempts count
                response.setZoomSessionFormMessage("Wrong OTP: Attempts remaining: " + (3 - form.getClientOtpAttempts()));
                response.setZoomSessionFormMessageCode(-1); //code for wrong attempt of otp
            }
        } else {
            response.setZoomSessionFormMessage("No form found for the provided email.");
            response.setZoomSessionFormMessageCode(0); //redirect to student profile page
        }

        return response;
    }
    public ZoomSessionFormMessageResponse resendOTP(String clientEmail) {
        ZoomSessionFormMessageResponse response = new ZoomSessionFormMessageResponse();
        Optional<ZoomSessionForm> formOptional = zoomSessionFormRepository.findByClientEmail(clientEmail);

        if (formOptional.isPresent()) {
            ZoomSessionForm form = formOptional.get();
            String newOtp = generateOTP();
            form.setClientOTP(newOtp);
            form.setClientOTPExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 minutes from now
            zoomSessionFormRepository.save(form);

            // Send new OTP via email
            String subject = "Your New OTP from GuideBook";
            String text = "Your new OTP is " + newOtp + ". It is valid for 5 minutes.";
            emailServiceImpl.sendSimpleMessage(form.getClientEmail(), subject, text);

            response.setZoomSessionFormMessage("New OTP has been sent to your email.");
        } else {
            response.setZoomSessionFormMessage("No form found for the provided email.");
        }

        return response;
    }
    private String generateOTP() {
        // Implement OTP generation logic
        // For example, generate a 6-digit random number
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}

