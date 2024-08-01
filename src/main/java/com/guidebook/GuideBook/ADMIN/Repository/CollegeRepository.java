package com.guidebook.GuideBook.ADMIN.Repository;

import com.guidebook.GuideBook.ADMIN.Models.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
    College findCollegeByCollegeNameIgnoreCase(String name);


}