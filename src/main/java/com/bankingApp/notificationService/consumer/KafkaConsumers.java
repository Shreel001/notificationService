package com.bankingApp.notificationService.consumer;

import com.bankingApp.notificationService.service.EmailService;
import com.bankingApp.notificationService.service.ReadFileService;
import com.bankingApp.shared_events_library.UserRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumers {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReadFileService readFileService;

    private String subject = "Welcome to the Banking App Family!";

    @KafkaListener(topics = "user-registered", groupId = "${spring.kafka.consumer.group-id}")
    public ResponseEntity<?> sendEmail(UserRegisteredEvent event){
        try{
            String body = readFileService.readFileFromResource("welcome-email.txt");

            String customizedBody = body
                    .replace("${username}", event.getUsername())
                    .replace("${email}", event.getEmail());

            emailService.sendEmail(event.getEmail(), subject, customizedBody);
            return ResponseEntity.ok("Success!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
