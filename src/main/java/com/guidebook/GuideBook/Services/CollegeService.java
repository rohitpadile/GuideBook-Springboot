package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;

    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }
}