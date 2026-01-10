package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.ContactMessage;
import org.example.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactMessageRepository contactMessageRepository;

    public ContactMessage saveMessage(ContactMessage message) {
        return contactMessageRepository.save(message);
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAllByOrderBySentAtDesc();
    }

    public Optional<ContactMessage> getMessageById(Long id) {
        return contactMessageRepository.findById(id);
    }

    public void markAsRead(Long id) {
        contactMessageRepository.findById(id).ifPresent(message -> {
            message.setIsRead(true);
            contactMessageRepository.save(message);
        });
    }

    public void deleteMessage(Long id) {
        contactMessageRepository.deleteById(id);
    }

    public long getUnreadCount() {
        return contactMessageRepository.countByIsReadFalse();
    }
}