package com.ibm.pmc.ticket.notification.repositories;

import com.ibm.pmc.ticket.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface NotificationRepository extends JpaRepository<Notification, String>, QueryDslPredicateExecutor<Notification> {
}
