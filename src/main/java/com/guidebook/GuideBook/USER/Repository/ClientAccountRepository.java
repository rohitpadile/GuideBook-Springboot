package com.guidebook.GuideBook.USER.Repository;

import com.guidebook.GuideBook.USER.Models.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount, String> {

}
