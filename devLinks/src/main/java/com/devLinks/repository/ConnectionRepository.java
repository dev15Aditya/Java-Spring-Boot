package com.devLinks.repository;

import com.devLinks.enums.ConnectionStatus;
import com.devLinks.model.Connection;
import com.devLinks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConnectionRepository extends JpaRepository<Connection, UUID> {
    @Query("SELECT c FROM Connection c WHERE " +
            "(c.requester = :user1 AND c.receiver = :user2) " +
            "OR (c.requester = :user2 AND c.receiver = :user1)")
    Optional<Connection> findConnectionBetweenUsers(User user1, User user2);

    @Query("SELECT c FROM Connection c WHERE " +
            "(c.requester = :user OR c.receiver = :user) AND c.status = 'ACCEPTED'")
    List<Connection> findAllAcceptedConnections(User user);

    List<Connection> findByReceiverAndStatus(User receiver, ConnectionStatus status);

    List<Connection> findByRequesterAndStatus(User requester, ConnectionStatus status);

    @Query("SELECT COUNT(c) FROM Connection c WHERE " +
            "(c.requester = :user OR c.receiver = :user) AND c.status = 'ACCEPTED'")
    long countConnections(User user);
}
