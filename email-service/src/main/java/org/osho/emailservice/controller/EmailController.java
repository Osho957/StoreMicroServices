package org.osho.emailservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.osho.emailservice.dtos.SendEmailEventDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {


    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;

    public EmailController(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{email}")
    public SendEmailEventDto sendEmail(@PathVariable String email) {
        SendEmailEventDto emailDto = new SendEmailEventDto();
        emailDto.setTo(email);
        emailDto.setFrom("test_email");
        emailDto.setSubject("Test email");
        emailDto.setBody("gbaba8790@gmail.com");
        try {
            kafkaTemplate.send("sendEmail", objectMapper.writeValueAsString(emailDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailDto;
    }
}
