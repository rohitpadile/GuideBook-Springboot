package com.guidebook.GuideBook.Repository.cutomrepository;

import com.guidebook.GuideBook.Models.College;

import java.util.List;


public interface CustomCollegeRepository {
    List<College> findCollegeByEntranceExamNameIgnoreCase(String examName);
}

