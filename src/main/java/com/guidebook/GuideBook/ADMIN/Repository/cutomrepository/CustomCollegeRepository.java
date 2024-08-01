package com.guidebook.GuideBook.ADMIN.Repository.cutomrepository;

import com.guidebook.GuideBook.ADMIN.Models.College;

import java.util.List;


public interface CustomCollegeRepository {
    List<College> findCollegeByEntranceExamNameIgnoreCase(String examName);
}

