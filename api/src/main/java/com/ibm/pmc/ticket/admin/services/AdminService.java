package com.ibm.pmc.ticket.admin.services;

import com.ibm.pmc.ticket.admin.domain.Admin;
import com.ibm.pmc.ticket.admin.domain.AdminModule;
import com.ibm.pmc.ticket.admin.domain.QAdmin;
import com.ibm.pmc.ticket.admin.jsons.LoginRequest;
import com.ibm.pmc.ticket.admin.jsons.RegisterRequest;
import com.ibm.pmc.ticket.admin.repositories.AdminRepository;
import com.ibm.pmc.ticket.common.validation.*;
import com.mysema.query.BooleanBuilder;
import org.hibernate.exception.ConstraintViolationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ibm.pmc.ticket.common.validation.Error.DUPLICATE_USERNAME_OR_EMAIL;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public Optional<Admin> login(LoginRequest loginRequest) {
        BooleanBuilder where = new BooleanBuilder();
        QAdmin admin = QAdmin.admin;

        where.and(admin.username.eq(loginRequest.getUsernameOrEmail())
                .or(admin.email.eq(loginRequest.getUsernameOrEmail())));

        return Optional.ofNullable(adminRepository.findOne(where))
                .filter(currentAdmin -> BCrypt.checkpw(loginRequest.getPassword(), currentAdmin.getPassword()));
    }

    @Transactional
    public Admin register(RegisterRequest registerRequest) {
            Admin admin = Admin.newBuilder()
                    .withCreatedAt(OffsetDateTime.now())
                    .withEmail(registerRequest.getEmail())
                    .withPassword(registerRequest.getPassword())
                    .withUpdatedAt(OffsetDateTime.now())
                    .withUsername(registerRequest.getUsername())
                    .build();
            Set<AdminModule> adminModules = registerRequest.getModules().stream()
                    .map(module -> AdminModule.newBuilder()
                            .withModule(module)
                            .withAdmin(admin)
                            .build()).collect(Collectors.toSet());
            admin.setModules(adminModules);

            return adminRepository.save(admin);
    }
}
