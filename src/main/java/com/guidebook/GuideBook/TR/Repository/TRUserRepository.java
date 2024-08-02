package com.guidebook.GuideBook.TR.Repository;

import com.guidebook.GuideBook.TR.Models.TRUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TRUserRepository extends JpaRepository<TRUser, Long> {
    Optional<TRUser> findByTrUserFirstNameAndTrUserLastName(String trUserFirstName, String trUserLastName);

}
