package edu.icet.controller;

import edu.icet.dto.Email;
import edu.icet.entity.RegisterEntity;
import edu.icet.service.EmailSenderService;
import edu.icet.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class EmailSenderController {

    private final EmailSenderService service;
    private final RegisterService registerService;

    @PostMapping("/post")
    public String sendForgotPasswordEmail(@RequestBody Email request) {
        RegisterEntity user = registerService.findByEmail(request.getEmail());
        if (user != null) {
            String subject = "Password Reset Request - Your Password: " + user.getPassword();
            String body = "Click the link to reset your password.";
            service.sendEmail(request.getEmail(), subject, body);
            return "Password reset email sent successfully.";
        }
        return "Email not found.";
    }
}