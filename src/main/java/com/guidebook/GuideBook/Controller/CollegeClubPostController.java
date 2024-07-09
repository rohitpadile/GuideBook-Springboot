package com.guidebook.GuideBook.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guidebook.GuideBook.Models.CollegeClubPost;
import com.guidebook.GuideBook.Services.CollegeClubPostService;
//import com.guidebook.GuideBook.Services.S3Service;
import com.guidebook.GuideBook.dtos.AddCollegeClubPostRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CollegeClubPostController {

//    @Autowired
//    private S3Service s3Service;

//    @PostMapping("/uploadFiles")
//    public List<String> uploadFiles(@RequestParam("mediaFiles") MultipartFile[] files,
//                                    @RequestParam("collegeClubName") String collegeClubName)
//            throws IOException
//    {
//        List<String> filePaths = new ArrayList<>();
//        for (MultipartFile file : files) {
//            String filePath = s3Service.uploadFile(file, collegeClubName);
//            filePaths.add(filePath);
//        }
//        return filePaths;
//    }
//
//    @PostMapping("/createCollegeClubPost")
//    public CollegeClubPost createClubPost(@Valid @RequestBody AddCollegeClubPostRequest request) {
//        CollegeClubPost post = new CollegeClubPost();
//        post.setCollegeClubPostDescription(request.getCollegeClubPostDescription());
//        post.setCollegeClubPostMediaPaths(request.getCollegeClubPostMediaPaths());
//        // Save post to the database
//        return post;
//    }

}