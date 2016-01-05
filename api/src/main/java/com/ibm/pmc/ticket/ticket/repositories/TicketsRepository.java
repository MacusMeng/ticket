package com.ibm.pmc.ticket.ticket.repositories;


import com.ibm.pmc.ticket.ticket.domains.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface TicketsRepository extends JpaRepository<Ticket, String>,QueryDslPredicateExecutor<Ticket> {

	
}
