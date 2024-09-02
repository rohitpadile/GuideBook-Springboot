package com.guidebook.GuideBook.MEETHOST.Repository;

import com.guidebook.GuideBook.MEETHOST.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByIsActive(Integer isActive);

    Optional<Event> findByEventCode(String eventCode);
}
