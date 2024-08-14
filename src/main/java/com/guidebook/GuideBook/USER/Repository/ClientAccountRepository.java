package com.guidebook.GuideBook.USER.Repository;

import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount, String> {
    ClientAccount findByClientAccountEmail(String email);
    List<ClientAccount> findBySubscriptionEndDateBefore(Date date);
}
