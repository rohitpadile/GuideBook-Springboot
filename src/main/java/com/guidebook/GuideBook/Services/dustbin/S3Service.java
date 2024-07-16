//package com.guidebook.GuideBook.Services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//@Service
//public class S3Service {
//    @Autowired
//    private S3Client s3Client;
//
//    @Value("${aws.s3.bucket-name}")
//    private String bucketName;
//    @Autowired
//    public S3Service(S3Client s3Client) {
//        this.s3Client = s3Client;
//    }
//
//    public String uploadCollegeClubPostsMedia(MultipartFile file, String clubName) throws IOException {
//        String uniqueFileName = clubName + "_" + System.currentTimeMillis() + "_" + UUID.randomUUID() + "_" + file.getOriginalFilename();
//        String key = Paths.get("collegeClubPostsMedia", uniqueFileName).toString();
//
//        s3Client.putObject(PutObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(key)
//                        .build(),
//                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
//
//        return key;
//    }
//}