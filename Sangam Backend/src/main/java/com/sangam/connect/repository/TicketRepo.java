package com.sangam.connect.repository;

import com.sangam.connect.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepo extends JpaRepository<TicketEntity, UUID> {
    Optional<TicketEntity> findByTicketToken(String ticketToken);
}
