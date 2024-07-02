package com.guidebook.GuideBook.Repository.cutomrepository;

import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.dtos.FilteredStudentListRequest;

import java.util.List;

public interface CustomStudentRepository {
    List<Student> findStudentsByFiltersIgnoreCase(FilteredStudentListRequest filters);
}
