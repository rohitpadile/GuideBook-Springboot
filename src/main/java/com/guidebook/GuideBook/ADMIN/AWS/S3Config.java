//package com.guidebook.GuideBook.AWS;
//
//
//import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//
//@Configuration
//public class S3Config {
//
//    @Value("${aws.s3.region}")
//    private String awsRegion;
//
//    @Bean
//    public S3Client s3Client() {
//        return S3Client.builder()
//                .region(Region.of(awsRegion))
//                .credentialsProvider(DefaultCredentialsProvider.create())
//                .build();
//    }
//
////    to HARDCORE THE CREDENTIALS
//
////    @Bean
////    public S3Client s3Client() {
////        // Hardcoded AWS credentials
////        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create("your-access-key", "your-secret-key");
////
////        return S3Client.builder()
////                .region(Region.AP_SOUTH_1) // Specify your desired AWS region
////                .credentialsProvider(() -> awsCredentials)
////                .build();
////    }
//}
//
