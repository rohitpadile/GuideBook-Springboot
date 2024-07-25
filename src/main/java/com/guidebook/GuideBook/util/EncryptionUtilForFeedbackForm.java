package com.guidebook.GuideBook.util;

import jakarta.annotation.PostConstruct;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Component;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class EncryptionUtilForFeedbackForm {

    private static final String DELIMITER = "-";
    private static final String ENCODED_FORMAT = "feed%s-back%s-form%s-for%s-session%s";

    public static String encode(String uuid) {
        String[] parts = uuid.split(DELIMITER);
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid UUID format");
        }

        return String.format(ENCODED_FORMAT, parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    public static String decode(String encodedString) {
        String[] parts = encodedString.split("-");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid encoded string format");
        }

        return String.join(DELIMITER, parts);
    }
}