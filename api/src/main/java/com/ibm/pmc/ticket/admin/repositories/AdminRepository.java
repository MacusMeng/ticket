package com.ibm.pmc.ticket.admin.repositories;

import com.ibm.pmc.ticket.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface AdminRepository extends JpaRepository<Admin, String>, QueryDslPredicateExecutor<Admin> {
}
