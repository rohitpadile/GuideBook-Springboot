//package com.guidebook.GuideBook.Services;
//
//import com.guidebook.GuideBook.Models.CollegeClub;
//import com.guidebook.GuideBook.Models.CollegeClubPost;
//import com.guidebook.GuideBook.Repository.CollegeClubPostRepository;
//import com.guidebook.GuideBook.Repository.CollegeClubRepository;
//import com.guidebook.GuideBook.dtos.AddCollegeClubPostRequest;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class CollegeClubPostService {
//
//    @Autowired
//    private CollegeClubPostRepository collegeClubPostRepository;
//
//    @Autowired
//    private CollegeClubRepository collegeClubRepository;
//
//    @Transactional
//    public void createPost(AddCollegeClubPostRequest request) {
//        CollegeClub collegeClub = collegeClubRepository.findByCollegeClubNameIgnoreCase(request.getCollegeClubName())
//                .orElseThrow(() -> new IllegalArgumentException("College club not found"));
//
//        //ADD COLLEGE CLUB NOT FOUND CUSTOM EXCEPTION
//
//        CollegeClubPost post = CollegeClubPost.builder()
//                .collegeClubPostDescription(request.getCollegeClubPostDescription())
//                .collegeClubPostMediaPaths(request.getCollegeClubPostMediaPaths())
//                .collegeClubPostClub(collegeClub)
//                .build();
//
//        collegeClubPostRepository.save(post);
//    }
//}
