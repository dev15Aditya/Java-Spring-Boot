package com.devLinks.controller;

import com.devLinks.model.Connection;
import com.devLinks.model.User;
import com.devLinks.repository.UserRepository;
import com.devLinks.service.AuthService;
import com.devLinks.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
public class ConnectionController {
    private final ConnectionService connectionService;
    private final AuthService authService;

    private UserRepository userRepository;

    @PostMapping("/request/{receiverId}")
    public ResponseEntity<String> sendRequest(@PathVariable Long receiverId){
        Long requesterId = getRequesterId();
        connectionService.sendConnectionRequest(requesterId, receiverId);

        return ResponseEntity.ok("Connection request sent successfully");
    }

    @PostMapping("/{connectionId}/respond")
    public ResponseEntity<String> respondToRequest(@PathVariable UUID connectionId, @RequestParam boolean accept){
        connectionService.respondToRequest(connectionId, accept);
        return ResponseEntity.ok(
                accept ? "Connection Accepted" : "Connection Rejected"
        );
    }

    @GetMapping
    public List<Connection> getMyConnections(){
        Long userId = getRequesterId();
        return connectionService.getUserConnections(userId);
    }

//    received=true means "requests I received"
//    received=false means "requests I sent"
    @GetMapping("/pending")
    public List<Connection> getPending(@RequestParam(defaultValue = "true") boolean received){
        Long userId = getRequesterId();
        return connectionService.getPendingRequests(userId, received);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getConnectionCount(){
        Long userId = getRequesterId();
        return ResponseEntity.ok(connectionService.countConnections(userId));
    }

    private Long getRequesterId(){
        String email = authService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getId();
    }
}
