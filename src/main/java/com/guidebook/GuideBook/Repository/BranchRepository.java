package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
