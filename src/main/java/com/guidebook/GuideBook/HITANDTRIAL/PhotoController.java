//package com.guidebook.GuideBook.HITANDTRIAL;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@CrossOrigin(origins = {
//        "http://localhost:3000", "http://localhost:8080",
//        "https://www.guidebookx.com","https://guidebookx.com",
//        "https://api.guidebookx.com",
//        "https://diugkigakpnwm.cloudfront.net"})
//@RestController
//@RequestMapping("/api/photos")
//public class PhotoController {
//
//
//    private final S3Service s3Service;
//    @Autowired
//    public PhotoController(S3Service s3Service) {
//        this.s3Service = s3Service;
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
//        String fileName = s3Service.uploadFile(file);
//        return ResponseEntity.ok("Photo uploaded successfully: " + fileName);
//    }
//}
//
