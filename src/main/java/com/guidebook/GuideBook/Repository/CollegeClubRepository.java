package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Models.CollegeClub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeClubRepository  extends JpaRepository<CollegeClub, String> {
    CollegeClub findByCollegeClubNameIgnoreCase(String name);
}
