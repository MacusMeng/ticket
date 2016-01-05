package com.ibm.pmc.ticket.common.mail;

import com.google.common.collect.Maps;
import com.ibm.pmc.ticket.notification.domain.NotificationCategory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.concurrent.ListenableFuture;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private VelocityEngine velocityEngine;

    @Async
    public ListenableFuture<Void> sendEmail(MimeMessagePreparator mimeMessagePreparator, Optional<Map<String, Object>> modelOpt, NotificationCategory notificationCategory, Locale locale) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessagePreparator.prepare(mimeMessageHelper.getMimeMessage());
            Map<String, Object> model = modelOpt.orElse(Maps.newHashMap());
            model.put("messageSource", messageSource);
            model.put("locale", locale);
            String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    notificationCategory + ".vm", StandardCharsets.UTF_8.toString(), model);
            mimeMessageHelper.setText(content, true);
        };

        javaMailSender.send(messagePreparator);
        return new AsyncResult<>(null);
    }
}