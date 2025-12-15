package com.devLinks.controller;

import com.devLinks.dto.ChangePasswordRequest;
import com.devLinks.dto.ForgotPasswordRequest;
import com.devLinks.dto.ResetPasswordRequest;
import com.devLinks.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class PasswordController {
    private PasswordService passwordService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgot(@RequestBody ForgotPasswordRequest req){
        passwordService.forgotPassword(req.getEmail());
        return ResponseEntity.ok("Reset link sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> reset(@RequestBody ResetPasswordRequest req){
        passwordService.resetPassword(req.getToken(), req.getNewPassword());
        return ResponseEntity.ok("Password updated.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> change(@RequestBody ChangePasswordRequest req){
        passwordService.changePassword(req.getOldPassword(), req.getNewPassword());
        return ResponseEntity.ok("Password changed");
    }
}
