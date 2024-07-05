package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
    College findCollegeByCollegeNameIgnoreCase(String name);

    @Query("SELECT c FROM College c JOIN c.collegeEntranceSet e WHERE LOWER(e.entranceExamName) = LOWER(:examName)")
    List<College> findCollegeByEntranceExamNameIgnoreCase(@Param("examName") String examName);
}