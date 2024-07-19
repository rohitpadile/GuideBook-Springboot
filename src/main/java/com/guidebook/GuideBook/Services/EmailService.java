//package com.guidebook.GuideBook.Services;
//
//import jakarta.mail.internet.MimeMessage;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class EmailServiceImpl implements EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//    @Value("${spring.mail.username}")
//    private String fromEmail;
//    @Override
//    public Object sendMail(EmailDto emailDto) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//
//            mimeMessageHelper.setFrom(fromEmail);
//            mimeMessageHelper.setTo(emailDto.getToList());
//            mimeMessageHelper.setSubject(emailDto.getSubject());
//            mimeMessageHelper.setText(emailDto.getBody());
//
//            javaMailSender.send(mimeMessage);
//
//            log.info("Message Sent Successfully to: {}", emailDto.getToList());
//            return new Response("Email Sent Successfully",emailDto.getToList(),ResponseMessage.SUCCESS);
//        }
//        catch (Exception e) {
//            log.error("sendEmail() | Error : {}", e.getMessage());
//            throw new RuntimeException(e);
//        }
//
//
//    }
//
//
//}
