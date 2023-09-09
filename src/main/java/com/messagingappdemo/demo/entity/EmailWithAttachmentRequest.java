package com.messagingappdemo.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailWithAttachmentRequest {

    private String to;
    private String subject;
    private String text;
    private String replyTo;
    private String attachmentUrl;

    public EmailWithAttachmentRequest(String to, String subject, String text) {
        this.to=to;
        this.subject=subject;
        this.text=text;
    }
}
