package com.messagingappdemo.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.messagingappdemo.demo.dto.ApiResponse;
import com.messagingappdemo.demo.dto.WhatsAppRequest;
import com.messagingappdemo.demo.entity.EmailRequest;
import com.messagingappdemo.demo.entity.EmailWithAttachmentRequest;
import com.messagingappdemo.demo.entity.OtpEmailRequest;
import com.messagingappdemo.demo.entity.PostScheduleEmailRequest;
import org.springframework.jms.annotation.JmsListener;

import javax.mail.MessagingException;

public interface MailService {
    ApiResponse sendEmail(EmailRequest emailRequest);

    ApiResponse sendEmailWithAttachment(EmailWithAttachmentRequest emailRequest) throws MessagingException;

    ApiResponse sendOtpEmail(OtpEmailRequest otpEmailRequest) throws MessagingException;

    ApiResponse sendPostScheduleEmail(PostScheduleEmailRequest postScheduleEmailRequest) throws MessagingException;

    //TODO. configure endpoint for send with attachment
    ApiResponse sendWhatsAppMessage(WhatsAppRequest whatsAppRequest);

    String getCurrentDate();

}
