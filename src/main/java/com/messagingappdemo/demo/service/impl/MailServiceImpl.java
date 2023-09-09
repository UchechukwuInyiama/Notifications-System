package com.messagingappdemo.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messagingappdemo.demo.dto.*;
import com.messagingappdemo.demo.entity.EmailRequest;
import com.messagingappdemo.demo.entity.EmailWithAttachmentRequest;
import com.messagingappdemo.demo.entity.OtpEmailRequest;
import com.messagingappdemo.demo.entity.PostScheduleEmailRequest;
import com.messagingappdemo.demo.service.MailService;
import com.messagingappdemo.demo.utill.MappingUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Strings;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    private final TemplateEngine templateEngine;

    public static final String ACCOUNT_SID = "ACe03aece821878f9b5bc02db6166eb1f1";
    public static final String AUTH_TOKEN = "c2cddb62dc974b41d74ce7cab6caeabc";

    @Override
    public ApiResponse sendEmail(EmailRequest emailRequest) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailRequest.getTo());
        msg.setCc(emailRequest.getCc());
        msg.setBcc(emailRequest.getBcc());
        msg.setReplyTo(emailRequest.getReplyTo());
        msg.setSubject(emailRequest.getSubject());
        msg.setText(emailRequest.getText());
        javaMailSender.send(msg);
return ApiResponse.getSuccessfulResponse(msg.toString());
    }
    //TODO. configure endpoint for send with attachment

    @Override
    public ApiResponse sendEmailWithAttachment(EmailWithAttachmentRequest emailRequest) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, true);
       helper.setTo(emailRequest.getTo());
        helper.setSubject(emailRequest.getSubject());
        helper.setText(emailRequest.getText(), true);

        if(!Strings.isNullOrEmpty(emailRequest.getAttachmentUrl())){
            File file=new File(emailRequest.getAttachmentUrl());
            String fileName=file.getName();
            helper.addAttachment(fileName,new File(emailRequest.getAttachmentUrl()));
        }
javaMailSender.send(mimeMessage);
        return ApiResponse.getSuccessfulResponse("Successful");
    }

    @Override
    public ApiResponse sendOtpEmail(OtpEmailRequest otpEmailRequest) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, true);
        helper.setTo(otpEmailRequest.getTo());
        helper.setSubject(otpEmailRequest.getSubject());
        helper.setText(otpEmailRequest.getText(), true);
        javaMailSender.send(mimeMessage);
        return ApiResponse.getSuccessfulResponse("Successful");
    }

    @Override
    public ApiResponse sendPostScheduleEmail(PostScheduleEmailRequest postScheduleEmailRequest) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, true);
        helper.setTo(postScheduleEmailRequest.getTo());
        helper.setSubject(postScheduleEmailRequest.getSubject());
        helper.setText(postScheduleEmailRequest.getText(), true);
        javaMailSender.send(mimeMessage);
        return ApiResponse.getSuccessfulResponse("Successful");
    }

    @Override
    public ApiResponse sendWhatsAppMessage(WhatsAppRequest whatsAppRequest){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:"+whatsAppRequest.getPhoneNumber()),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                whatsAppRequest.getText()).create();
        return new ApiResponse(
                new WhatsAppResponse(
                   message.getErrorMessage(),String.valueOf(message.getStatus())
                )
        );
    }

    @Override
    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance(); // Returns instance with current date and time set
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d YYYY, hh:mm aaa");
        return formatter.format(calendar.getTime());
    }

    @JmsListener(destination = "email-message")
    public void getEmailMessageFromQueue(String payload) throws JsonProcessingException {
        QueueDto queueDto = objectMapper.readValue(payload, QueueDto.class);
        String msg=" Hello "+queueDto.getFirstName()+"\n Welcome to EasyKobo.Ng.\n New sign-in at "+getCurrentDate();
        EmailRequest emailRequest = MappingUtil.mapToEmailRequest(queueDto,"Security Alert",msg);
        sendEmail(emailRequest);
    }

    @JmsListener(destination = "verification-message")
    public void verificationMessage(String payload) throws JsonProcessingException {
        QueueVerificationMessageDto queueDto = objectMapper.readValue(payload, QueueVerificationMessageDto.class);
        EmailRequest emailRequest = MappingUtil.mapToEmailRequest( queueDto, "Verification");
        sendEmail(emailRequest);
    }

@JmsListener(destination = "whatsApp-message")
public void queueWhatsAppMessage(String payload) throws JsonProcessingException {
        QueueDto queueDto=objectMapper.readValue(payload,QueueDto.class);
        String msg="Hello "+queueDto.getFirstName()+"\n Welcome to EasyKobo.Ng.\n New sign-in at "+getCurrentDate();
WhatsAppRequest whatsAppRequest=MappingUtil.mapToWhatsAppRequest(queueDto,msg);
sendWhatsAppMessage(whatsAppRequest);
}

    @JmsListener(destination = "email-attachment-message")
    public void sendEmailWithAttachment(String payload) throws MessagingException, JsonProcessingException {
        QueueDto queueDto=objectMapper.readValue(payload,QueueDto.class);
        Locale locale = Locale.getDefault();
        Context ctx = new Context(locale);
        ctx.setVariable("firstName",queueDto.getFirstName());
        ctx.setVariable("time",getCurrentDate());
        String text=templateEngine.process("email.html",ctx);
        EmailWithAttachmentRequest emailWithAttachmentRequest= MappingUtil.mapToEmailWithAttachmentRequest(queueDto,"Testing",text);
        sendEmailWithAttachment(emailWithAttachmentRequest);
    }

    @JmsListener(destination = "otp-email-message")
    public void sendOtpEmail(String payload) throws MessagingException, JsonProcessingException {
       OtpQueueDto otpQueueDto = objectMapper.readValue(payload, OtpQueueDto.class);
        Locale locale = Locale.getDefault();
        Context ctx = new Context(locale);
        ctx.setVariable("username", otpQueueDto.getUsername());
        ctx.setVariable("otp", otpQueueDto.getOtp());
        ctx.setVariable("time",getCurrentDate());
        String text = templateEngine.process("otpemail.html",ctx);
        OtpEmailRequest otpEmailRequest = MappingUtil.mapToOtpEmailRequest(otpQueueDto, "Otp", text, otpQueueDto.getOtp());
        sendOtpEmail(otpEmailRequest);
    }

    @JmsListener(destination = "post-schedule-email-message")
    public void sendPostScheduleEmail(String payload) throws MessagingException, JsonProcessingException {
        PostScheduleDto postScheduleDto = objectMapper.readValue(payload, PostScheduleDto.class);
        Locale locale = Locale.getDefault();
        Context context = new Context(locale);
        context.setVariable("username", postScheduleDto.getUsername());
        context.setVariable("post", postScheduleDto.getPost());
        context.setVariable("time",getCurrentDate());
        String text = templateEngine.process("postScheduleEmail.html", context);
        PostScheduleEmailRequest postScheduleEmailRequest = MappingUtil.mapToPostScheduleEmailRequest(postScheduleDto, "Email Schedule", text, postScheduleDto.getPost());
        sendPostScheduleEmail(postScheduleEmailRequest);
    }
}
