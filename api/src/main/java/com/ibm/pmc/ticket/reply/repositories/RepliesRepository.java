package com.ibm.pmc.ticket.reply.repositories;


import com.ibm.pmc.ticket.reply.domains.Reply;
import com.ibm.pmc.ticket.ticket.domains.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface RepliesRepository extends JpaRepository<Reply, String>,QueryDslPredicateExecutor<Reply> {

	
}
