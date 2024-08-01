package com.guidebook.GuideBook.ADMIN.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class EncryptionUtilForZoomSessionCancel {

    private static final SecureRandom random = new SecureRandom();
    private static final int RANDOM_NUM_LENGTH = 5; // 5-digit random number

    public static String encrypt(String zoomSessionFormId, String studentWorkEmail) {
        StringBuilder combinedString = new StringBuilder(zoomSessionFormId)
                .append(studentWorkEmail);
        StringBuilder encrypted = new StringBuilder();

        for (char ch : combinedString.toString().toCharArray()) {
            encrypted.append(ch);
            encrypted.append(generateRandomNumber(RANDOM_NUM_LENGTH));
        }

        return encrypted.toString();
    }

    private static String generateRandomNumber(int length) {
        StringBuilder number = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            number.append(random.nextInt(10)); // Append a random digit (0-9)
        }

        return number.toString();
    }
}


