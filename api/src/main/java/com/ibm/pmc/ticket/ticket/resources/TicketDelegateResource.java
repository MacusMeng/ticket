package com.ibm.pmc.ticket.ticket.resources;

import com.google.common.collect.Lists;
import com.ibm.pmc.ticket.admin.domain.Admin;
import com.ibm.pmc.ticket.common.validation.ConflictException;
import com.ibm.pmc.ticket.common.validation.Error;
import com.ibm.pmc.ticket.reply.jsons.ReplyRequest;
import com.ibm.pmc.ticket.ticket.domains.Status;
import com.ibm.pmc.ticket.ticket.domains.Ticket;
import com.ibm.pmc.ticket.ticket.jsons.Identity;
import com.ibm.pmc.ticket.ticket.jsons.TicketCreateRequest;
import com.ibm.pmc.ticket.ticket.jsons.TicketFileInfo;
import com.ibm.pmc.ticket.ticket.jsons.TicketInfo;
import com.ibm.pmc.ticket.ticket.services.TicketsService;
import com.ibm.pmc.ticket.user.transferdomains.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TicketDelegateResource {
    private static final Logger logger = LoggerFactory.getLogger(TicketResource.class);

    @Autowired
    private TicketsService ticketsService;

    public Page<Ticket> queryTickets(Optional<String> status,
                                     Optional<Long> number,
                                     Integer page,
                                     Integer pageSize,
                                     Admin currentAdmin,
                                     User currentUser) {
        return ticketsService.findTicket(
                status,
                number,
                Optional.ofNullable(currentAdmin),
                Optional.ofNullable(currentUser),
                new PageRequest(
                        page - 1,
                        pageSize,
                        new Sort(Sort.Direction.DESC, "updatedAt")));
    }

    public TicketInfo queryTicketInfo(String id) {
        Ticket ticket = ticketsService.findTicketById(id);

        List<TicketFileInfo> fileInfos = Lists.newArrayList();
        java.nio.file.Path path = Paths.get(System.getProperty("user.home")
                + "/pmc_ticket/" + id);
        if (Files.exists(path)) {
            try {
                Files.walkFileTree(path, new SimpleFileVisitor<java.nio.file.Path>() {
                    @Override
                    public FileVisitResult visitFile(java.nio.file.Path file, BasicFileAttributes basicFileAttributes) throws IOException {
                        fileInfos.add(TicketFileInfo.newBuilder()
                                .withFileName(file.toFile().getName())
                                .withContentType(Files.probeContentType(file))
                                .build());
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                logger.error("Find ticket files exception", e);
                throw new RuntimeException(com.ibm.pmc.ticket.common.validation.Error.FIND_TICKET_FILES_EXCEPTION.toString());
            }
        }

        List<ReplyRequest> replyRequest = ticket.getReplies()
                .stream()
                .map(reply -> ReplyRequest.ReplyInfoBuilder.replyInfo()
                        .withUserId(reply.getUserId())
                        .withCreatedAt(reply.getCreatedAt())
                        .withUsername(reply.getUsername())
                        .withAdminName(reply.getAdmin() == null ? "" : reply.getAdmin().getUsername())
                        .withContent(reply.getContent())
                        .withId(reply.getId())
                        .withTicketId(reply.getTicket().getId())
                        .build())
                .collect(Collectors.toList());

        return TicketInfo.newBuilder()
                .withId(ticket.getId())
                .withUserId(ticket.getUserId())
                .withUsername(ticket.getUsername())
                .withTitle(ticket.getTitle())
                .withContent(ticket.getContent())
                .withModule(ticket.getModule())
                .withNumber(ticket.getNumber())
                .withReplies(replyRequest)
                .withStatus(ticket.getStatus())
                .withCreatedAt(ticket.getCreatedAt())
                .withUpdatedAt(ticket.getUpdatedAt())
                .withFileNames(fileInfos)
                .build();
    }

    public Identity generateTicketIdentity() {
        return Identity.newBuilder().withId(UUID.randomUUID().toString()).build();
    }

    public void upload(InputStream inputStream,
                       String ticketId,
                       String fileName) {
        try {
            java.nio.file.Path path = Paths.get(System.getProperty("user.home")
                    + "/pmc_ticket/" + ticketId + "/" + fileName);
            if (Files.exists(path)) {
                throw new ConflictException(Error.FILE_ALREADY_EXIST);
            }
            Files.createDirectories(path.getParent());
            Files.copy(inputStream, path);
        } catch (IOException e) {
            logger.error("File upload io exception", e);
            throw new RuntimeException(Error.FILE_UPLOAD_IO_EXCEPTION.toString());
        }
    }

    public void submit(TicketCreateRequest ticketCreateRequest, User currentUser) {
        Ticket ticket = Ticket.TicketBuilder.ticket()
                .withContent(ticketCreateRequest.getContent())
                .withCreatedAt(OffsetDateTime.now())
                .withId(ticketCreateRequest.getId())
                .withUpdatedAt(OffsetDateTime.now())
                .withUsername(currentUser.getUsername())
                .withModule(ticketCreateRequest.getModule())
                .withStatus(Status.CREATED)
                .withTitle(ticketCreateRequest.getTitle())
                .withUserId(currentUser.getUserId())
                .withUserMail(currentUser.getEmail())
                .build();

        ticketsService.saveTicket(ticket);
    }

    public void delete(String id,
                       String fileName) {
        try {
            java.nio.file.Path path = Paths.get(System.getProperty("user.home")
                    + "/pmc_ticket/" + id + "/" + fileName);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            logger.error("File delete io exception", e);
            throw new RuntimeException(Error.FILE_DELETE_IO_EXCEPTION.toString());
        }
    }

    public void findFile(String id,
                         String fileName,
                         HttpServletResponse response) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            java.nio.file.Path path = Paths.get(System.getProperty("user.home")
                    + "/pmc_ticket/" + id + "/" + fileName);

            Files.copy(path, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error("File find io exception", e);
            throw new RuntimeException(Error.FIND_TICKET_FILES_EXCEPTION.toString());
        }
    }

    public void downloadFile(String id,
                             String fileName,
                             HttpServletResponse response) {
        java.nio.file.Path path = Paths.get(System.getProperty("user.home")
                + "/pmc_ticket/" + id + "/" + fileName);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
                    + ";"
                    + "filename*=UTF-8''"
                    + URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()));
            Files.copy(path, outputStream);
            response.flushBuffer();
        } catch (IOException e) {
            logger.error("File find io exception", e);
            throw new RuntimeException(Error.FIND_TICKET_FILES_EXCEPTION.toString());
        }
    }
}
