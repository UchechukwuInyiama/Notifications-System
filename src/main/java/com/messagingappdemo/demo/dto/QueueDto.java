package com.messagingappdemo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueDto {
    private String email;
    private String firstName;
    private String templateName;
    private String phoneNumber;
    private String message;
}
