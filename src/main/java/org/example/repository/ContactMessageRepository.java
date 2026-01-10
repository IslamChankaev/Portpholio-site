package org.example.repository;

import org.example.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage> findAllByOrderBySentAtDesc();
    List<ContactMessage> findByIsRead(Boolean isRead);
    long countByIsReadFalse();
}