package com.ibm.pmc.ticket.notification.services;

import com.ibm.pmc.ticket.notification.domain.Notification;
import com.ibm.pmc.ticket.notification.domain.NotificationStatus;
import com.ibm.pmc.ticket.notification.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public void addNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Transactional
    public void updateNotificationStatus(Notification notification, NotificationStatus status) {
        notification.setNotificationStatus(status);
        notification.setUpdatedAt(OffsetDateTime.now());
        notificationRepository.save(notification);
    }
}
