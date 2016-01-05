package com.ibm.pmc.ticket.ticket.services;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.ibm.pmc.ticket.admin.domain.Admin;
import com.ibm.pmc.ticket.admin.domain.AdminModule;
import com.ibm.pmc.ticket.admin.domain.Module;
import com.ibm.pmc.ticket.common.validation.NumberValidation;
import com.ibm.pmc.ticket.ticket.domains.QTicket;
import com.ibm.pmc.ticket.ticket.domains.Status;
import com.ibm.pmc.ticket.ticket.domains.Ticket;
import com.ibm.pmc.ticket.ticket.repositories.TicketsRepository;
import com.ibm.pmc.ticket.user.transferdomains.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysema.query.BooleanBuilder;

@Service
public class TicketsService {

    @Autowired
    private TicketsRepository ticketsRepository;



    @Transactional(readOnly = true)
    public Page<Ticket> findTicket(Optional<String> status,
                                   Optional<Long> number,
                                   Optional<Admin> admin,
                                   Optional<User> user,
                                   Pageable pageable) {
        BooleanBuilder where = new BooleanBuilder();
        QTicket ticket = QTicket.ticket;
        if (user.isPresent()) {
            where.and(ticket.userId.eq(user.get().getUserId()));
        }

        if (admin.isPresent()) {
            Set<Module> modules = admin.get().getModules().stream()
                    .map(AdminModule::getModule).collect(Collectors.toSet());
            modules.add(Module.OTHER);
            where.and(ticket.module.in(modules));
        }

        if (status.isPresent()) {
            List<String> statuses= Splitter.on(",").splitToList(status.get());
            where.and(ticket.status.in(statuses.stream().map(Status::valueOf).collect(Collectors.toList())));
        }

        if (number.isPresent()) {
            where.and(ticket.number.eq(number.get()));
        }

        return ticketsRepository.findAll(where, pageable);
    }

    @Transactional(readOnly = true)
    public Ticket findTicketById(String id) {
        return ticketsRepository.findOne(id);
    }

    @Transactional
    public void saveTicket(Ticket ticket) {
        ticketsRepository.save(ticket);
    }

    @Transactional
    public void updateTicketStatus(String id, Status status) {
        Ticket ticket = findTicketById(id);
        ticket.setStatus(status);
        ticket.setUpdatedAt(OffsetDateTime.now());
        ticketsRepository.save(ticket);
    }
}
