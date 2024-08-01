package com.guidebook.GuideBook.ADMIN.Repository.cutomrepository;

import com.guidebook.GuideBook.ADMIN.dtos.filterstudents.FilteredStudentListRequest;
import com.guidebook.GuideBook.ADMIN.Models.Student;

import java.util.List;

public interface CustomStudentRepository {
    List<Student> findStudentsByFiltersIgnoreCase(FilteredStudentListRequest filters);
}
