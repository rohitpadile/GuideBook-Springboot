package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
