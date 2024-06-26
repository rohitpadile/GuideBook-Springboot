package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
}