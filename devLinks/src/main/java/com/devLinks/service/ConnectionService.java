package com.devLinks.service;

import com.devLinks.enums.ConnectionStatus;
import com.devLinks.exception.BadRequestException;
import com.devLinks.exception.ResourceNotFoundException;
import com.devLinks.model.Connection;
import com.devLinks.model.User;
import com.devLinks.repository.ConnectionRepository;
import com.devLinks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConnectionService {
    private final ConnectionRepository connectionRepo;
    private final UserRepository userRepo;

    public void sendConnectionRequest(Long requesterId, Long receiverId){
        if(requesterId.equals(receiverId)){
            throw new BadRequestException("Cannot connect to yourself");
        }

        User requester = userRepo.findById(requesterId)
                .orElseThrow(() -> new ResourceNotFoundException("Requester not found"));
        User receiver = userRepo.findById(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));

        connectionRepo.findConnectionBetweenUsers(requester, receiver)
                .ifPresent(c -> {
                    throw new BadRequestException("Connection already exists");
                });

        Connection connection = Connection.builder()
                .requester(requester)
                .receiver(receiver)
                .status(ConnectionStatus.PENDING)
                .requestedAt(LocalDateTime.now())
                .build();

        connectionRepo.save(connection);
    }

    public void respondToRequest(UUID connectionId, boolean accept) {
        Connection connection = connectionRepo.findById(connectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Connection not found"));

        if(connection.getStatus() != ConnectionStatus.PENDING){
            throw new BadRequestException("Request already responded to");
        }

        connection.setStatus(accept ? ConnectionStatus.ACCEPTED : ConnectionStatus.REJECTED);
        connection.setRespondedAt(LocalDateTime.now());
        connectionRepo.save(connection);
    }

    public List<Connection> getUserConnections(Long userId){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return connectionRepo.findAllAcceptedConnections(user);
    }

    public List<Connection> getPendingRequests(Long userId, boolean received){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return received
                ? connectionRepo.findByReceiverAndStatus(user, ConnectionStatus.PENDING)
                : connectionRepo.findByRequesterAndStatus(user, ConnectionStatus.PENDING);
    }

    public long countConnections(Long userId){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return connectionRepo.countConnections(user);
    }
}
