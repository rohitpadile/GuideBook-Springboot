package com.guidebook.GuideBook.ADMIN.Repository;

import com.guidebook.GuideBook.ADMIN.Models.EntranceExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntranceExamRepository extends JpaRepository<EntranceExam, Long> {

    EntranceExam findByEntranceExamNameIgnoreCase(String examName);

}
