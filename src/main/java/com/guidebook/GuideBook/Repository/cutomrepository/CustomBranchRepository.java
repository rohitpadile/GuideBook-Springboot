package com.guidebook.GuideBook.Repository.cutomrepository;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeRequest;

import java.util.List;

public interface CustomBranchRepository {

    List<Branch> findBranchesForCollegeIgnoreCase(GetAllBranchNameListForCollegeRequest request);
}
