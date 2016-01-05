package com.ibm.pmc.ticket.common.mail;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.ibm.pmc.ticket.notification.domain.NotificationStatus;
import com.ibm.pmc.ticket.notification.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;
import java.util.Optional;

import static com.ibm.pmc.ticket.notification.domain.NotificationCategory.ENGINEER_REPLIED;
import static javax.mail.Message.RecipientType.TO;

@Component
public class MailEventListener {

    private static final Logger logger = LoggerFactory.getLogger(MailEventListener.class);

    @Autowired
    private MailService mailService;

    @Value("${mail.from.address}")
    private String from;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private NotificationService notificationService;

    @Subscribe
    public void fire(MailEvent mailEvent) {
        if (mailEvent.getNotification().getNotificationCategory() == ENGINEER_REPLIED) {
            Map<String, Object> argsMap = Maps.newHashMap();
            argsMap.put("lineOneArgs", new Object[]{mailEvent.getNotification().getTicketId().getUsername()});
            argsMap.put("lineTwoArgs", new Object[]{mailEvent.getNotification().getTicketId().getNumber() + ""});
            ListenableFuture<Void> future = mailService.sendEmail(mimeMessage -> {
                        mimeMessage.setFrom(from);
                        mimeMessage.setRecipients(TO,
                                mailEvent.getNotification().getTicketId().getUserMail());
                        mimeMessage.setSubject(messageSource.getMessage("mail." + ENGINEER_REPLIED.toString().toLowerCase() + ".subject", new Object[]{}, mailEvent.getLocale()));
                    },
                    Optional.of(argsMap),
                    mailEvent.getNotification().getNotificationCategory(), mailEvent.getLocale());

            future.addCallback(aVoid -> notificationService.updateNotificationStatus(mailEvent.getNotification(), NotificationStatus.SUCESSED),
                    ex -> {
                        logger.error("Send Mail Exception", ex);
                        notificationService.updateNotificationStatus(mailEvent.getNotification(), NotificationStatus.FAILED);
                    });
        }

    }


}

