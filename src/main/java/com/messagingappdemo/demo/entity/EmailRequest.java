package com.messagingappdemo.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    private String[]cc;

    public EmailRequest(String to, String subject, String text) {
        this.to = to;
        this.subject=subject;
        this.text=text;
    }
    public EmailRequest(String subject, String text) {
        this.subject=subject;
        this.text=text;
    }

    private String[] bcc;
    private String to;
    private String subject;
    private String text;
    private String replyTo;

}
