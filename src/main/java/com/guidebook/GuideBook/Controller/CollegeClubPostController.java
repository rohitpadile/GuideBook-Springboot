
package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.CollegeClubPost;
import com.guidebook.GuideBook.Services.CollegeClubPostService;
//import com.guidebook.GuideBook.Services.S3Service;

import com.guidebook.GuideBook.Services.S3Service;
import com.guidebook.GuideBook.dtos.AddCollegeClubPostRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/college-club-posts")
public class CollegeClubPostController {

    @Autowired
    private CollegeClubPostService collegeClubPostService;

    @Autowired
    private S3Service s3Service;

    @PostMapping("/uploadCollegeClubPostMedia")
    public ResponseEntity<List<String>> uploadCollegeClubPostMedia(
            @RequestParam("mediaFiles") List<MultipartFile> mediaFiles,
            @RequestParam("collegeClubName") String collegeClubName)
            throws IOException
    {
        List<String> mediaPaths = s3Service.uploadCollegeClubPostMedia(mediaFiles, collegeClubName);
        return ResponseEntity.ok(mediaPaths);
    }

    @PostMapping("/createClubPost")
    public ResponseEntity<?> createClubPost(@RequestBody @Valid AddCollegeClubPostRequest request) {
        collegeClubPostService.createPost(request);
        return ResponseEntity.ok("Post created successfully");
    }
}
