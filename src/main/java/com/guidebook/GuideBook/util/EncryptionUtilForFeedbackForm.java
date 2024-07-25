package com.guidebook.GuideBook.util;

import jakarta.annotation.PostConstruct;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Component;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtilForFeedbackForm {

    private final AES256TextEncryptor textEncryptor;

    public EncryptionUtilForFeedbackForm() {
        String encryptionPassword = "feedbackFormPass"; // Hardcoded password
        if (encryptionPassword == null || encryptionPassword.isEmpty()) {
            throw new IllegalArgumentException("Encryption password cannot be empty");
        }
        this.textEncryptor = new AES256TextEncryptor();
        this.textEncryptor.setPassword(encryptionPassword);
    }

    public String encrypt(String input) {
        return textEncryptor.encrypt(input);
    }

    public String decrypt(String encrypted) {
        return textEncryptor.decrypt(encrypted);
    }
}