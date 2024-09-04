package com.guidebook.GuideBook.HITANDTRIAL;

//AKIAQ3EGTC7B24NCPMH7
//t23H0OOPBeTcqIx+getrguEMbpEb/AFSm1xmmKF7
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
    public class S3Service {
        @Value("${aws.s3.bucket-name}")
        private String bucketName;

        private S3Client s3Client;
        @Autowired
        public S3Service(S3Client s3Client) {
            this.s3Client = s3Client;
        }

        public String uploadFile(MultipartFile file) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            try {
                s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
                return fileName;
            } catch (IOException e) {
                throw new RuntimeException("Error uploading file to S3", e);
            }
        }
    }

