package com.guidebook.GuideBook.ADMIN.Repository.cutomrepository;

import com.guidebook.GuideBook.ADMIN.Models.Branch;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeRequest;

import java.util.List;

public interface CustomBranchRepository {

    List<Branch> findBranchesForCollegeIgnoreCase(GetAllBranchNameListForCollegeRequest request);
}
