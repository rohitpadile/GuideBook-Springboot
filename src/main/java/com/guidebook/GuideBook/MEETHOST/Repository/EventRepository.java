package com.guidebook.GuideBook.MEETHOST.Repository;

import com.guidebook.GuideBook.MEETHOST.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
}
