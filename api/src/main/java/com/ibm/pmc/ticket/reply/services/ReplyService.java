package com.ibm.pmc.ticket.reply.services;


import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.ibm.pmc.ticket.common.mail.MailEvent;
import com.ibm.pmc.ticket.common.mail.MailService;
import com.ibm.pmc.ticket.notification.domain.Notification;
import com.ibm.pmc.ticket.notification.domain.NotificationCategory;
import com.ibm.pmc.ticket.notification.domain.NotificationStatus;
import com.ibm.pmc.ticket.notification.domain.NotificationType;
import com.ibm.pmc.ticket.notification.services.NotificationService;
import com.ibm.pmc.ticket.reply.domains.Reply;
import com.ibm.pmc.ticket.reply.repositories.RepliesRepository;
import com.ibm.pmc.ticket.ticket.domains.Status;
import com.ibm.pmc.ticket.ticket.domains.Ticket;
import com.ibm.pmc.ticket.ticket.services.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.ibm.pmc.ticket.notification.domain.NotificationCategory.ENGINEER_REPLIED;
import static javax.mail.Message.RecipientType.TO;

@Service
public class ReplyService {

    @Autowired
    private RepliesRepository repliesRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageSource messageSource;

    @Value("${mail.from.address}")
    private String from;

    @Transactional
    public void addReply(Reply reply, Locale locale) {
        repliesRepository.saveAndFlush(reply);

        Ticket ticket = reply.getTicket();
        if (reply.getAdmin() == null) {
            if(ticket.getStatus()==Status.CREATED){
                ticket.setStatus(Status.CREATED);
                ticket.setUpdatedAt(OffsetDateTime.now());
                ticketsService.saveTicket(ticket);
            }
            else{
                ticket.setStatus(Status.USER_REPLY);
                ticket.setUpdatedAt(OffsetDateTime.now());
                ticketsService.saveTicket(ticket);
            }
            return;
        }

        Notification notification = Notification.newBuilder()
                .withNotificationType(NotificationType.EMAIL)
                .withUpdatedAt(OffsetDateTime.now())
                .withCreatedAt(OffsetDateTime.now())
                .withNotificationCategory(NotificationCategory.ENGINEER_REPLIED)
                .withNotificationStatus(NotificationStatus.CREATED)
                .withReply(reply)
                .withTicketId(ticket)
                .build();
        ticket.setStatus(Status.REPLIED);
        ticket.setUpdatedAt(OffsetDateTime.now());
        ticketsService.saveTicket(ticket);
        notificationService.addNotification(notification);

        eventBus.post(MailEvent.newBuilder()
                .withLocale(locale)
                .withNotification(notification)
                .build());
    }
}
