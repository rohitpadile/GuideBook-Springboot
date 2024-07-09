package com.guidebook.GuideBook.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guidebook.GuideBook.Models.CollegeClubPost;
import com.guidebook.GuideBook.Services.CollegeClubPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class CollegeClubPostController {

    private static final String MEDIA_UPLOAD_PATH = "public/collegeClubPostsMedia/";

    @Autowired
    private CollegeClubPostService collegeClubPostService;

    @PostMapping("/createClubPost")
    public ResponseEntity<CollegeClubPost> createClubPost(
            @RequestParam("postJson") String postJson,
            @RequestParam("mediaFiles") List<MultipartFile> mediaFiles) {

        ObjectMapper objectMapper = new ObjectMapper();
        CollegeClubPost collegeClubPost;
        try {
            collegeClubPost = objectMapper.readValue(postJson, CollegeClubPost.class);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        try {
            for (MultipartFile file : mediaFiles) {
                String fileName = collegeClubPost.getCollegeClubName() + "_" + UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(MEDIA_UPLOAD_PATH + fileName);
                Files.write(path, file.getBytes());
                collegeClubPost.getCollegeClubPostMediaPaths().add(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        CollegeClubPost createdPost = collegeClubPostService.save(collegeClubPost);
        return ResponseEntity.ok(createdPost);
    }
}