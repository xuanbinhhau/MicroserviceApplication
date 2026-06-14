package com.devteria.profile.controller;

import com.devteria.profile.dto.request.EmailRequest;
import com.devteria.profile.dto.request.SendEmailRequest;
import com.devteria.profile.dto.response.ApiResponse;
import com.devteria.profile.dto.response.EmailResponse;
import com.devteria.profile.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailController {

    EmailService emailService;

    @PostMapping("/email/send")
    ApiResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest emailRequest){
        return ApiResponse.<EmailResponse>builder()
                .result(emailService.sendEmail(emailRequest))
                .build();
    }


}
