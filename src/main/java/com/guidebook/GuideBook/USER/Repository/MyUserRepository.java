package com.guidebook.GuideBook.USER.Repository;

import com.guidebook.GuideBook.USER.Models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<MyUser, String> {
    MyUser findByUsername(String username);
}
