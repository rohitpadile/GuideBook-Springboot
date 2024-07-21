package com.guidebook.GuideBook.Services.zoomsessionbook;

import com.guidebook.GuideBook.Models.ZoomSessionForm;
import com.guidebook.GuideBook.Repository.ZoomSessionFormRepository;
import com.guidebook.GuideBook.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionFormRequest;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionFormMessageResponse;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionOTPResendRequest;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionOTPVerifyRequest;
import jakarta.transaction.Transactional;
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
    @Transactional
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
                .isVerified(0)
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
            String text = "Dear " + form.getClientFirstName() + ",\n\n" +
                    "Welcome to GuideBook! Your NEW OTP for verification is: " + newOtp + ".\n\n" +
                    "This OTP is valid for the next 5 minutes.\n\n" +
                    "Thank you for choosing GuideBook. We are dedicated to providing you with the best experience.\n\n" +
                    "Best Regards,\n" +
                    "GuideBook Team";
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
}

