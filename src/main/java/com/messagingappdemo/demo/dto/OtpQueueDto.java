package com.messagingappdemo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpQueueDto {
    private String email;
    private String username;
    private String otp;
    private String message;
}
