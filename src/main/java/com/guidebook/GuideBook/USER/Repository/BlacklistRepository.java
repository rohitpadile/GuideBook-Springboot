package com.guidebook.GuideBook.USER.Repository;

import com.guidebook.GuideBook.USER.Models.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<BlacklistedToken, String> {
}
