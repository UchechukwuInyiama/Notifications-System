package com.messagingappdemo.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostScheduleEmailRequest {
        private String to;
        private String subject;
        private String text;
        private String post;
}
