//package com.guidebook.GuideBook.AWS;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//
//@Configuration
//public class AmazonS3Config {
//
//    @Bean
//    public S3Client s3Client() {
//        return S3Client.builder()
//                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
//                .region(Region.AP_SOUTH_1) // Region for AWS S3 in Mumbai, India
//                .build();
//    }
//}
