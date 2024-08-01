//package com.guidebook.GuideBook.Services;
//
//import com.guidebook.GuideBook.Models.CollegeClubPost;
//import com.guidebook.GuideBook.Repository.CollegeClubPostRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class S3Service {
//
//    private final S3Client s3Client;
//
//    @Value("${aws.s3.bucket-name}")
//    private String bucketName;
//    @Autowired
//    public S3Service(S3Client s3Client) {
//        this.s3Client = s3Client;
//    }
//
//    public List<String> uploadCollegeClubPostMedia(List<MultipartFile> files, String clubName)
//            throws IOException
//    {
//        List<String> mediaPaths = new ArrayList<>();
//
//        for (MultipartFile file : files) {
//            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//            String key = "collegeClubPostsMedia/" + clubName + "/" + timestamp + "_" + file.getOriginalFilename();
//
//            try {
//                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(key)
//                        .build();
//
//                s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
//
//                mediaPaths.add(key);
//            } catch (Exception e) {
//                // Log the error and throw an IOException
//                System.err.println("Error uploading file to S3: " + file.getOriginalFilename() + " - " + e.getMessage());
//                throw new IOException("Error uploading file to S3", e);
//            }
//        }
//
//        return mediaPaths;
//    }
//}
