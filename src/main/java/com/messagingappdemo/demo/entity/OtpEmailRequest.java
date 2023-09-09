package com.messagingappdemo.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpEmailRequest {
    private String to;
    private String subject;
    private String text;
    private String otp;
    private String attachmentUrl;

    public OtpEmailRequest(String to, String subject, String text, String otp) {
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.otp = otp;
    }
}
