package com.messagingappdemo.demo.utill;

import com.messagingappdemo.demo.dto.*;
import com.messagingappdemo.demo.entity.EmailRequest;
import com.messagingappdemo.demo.entity.EmailWithAttachmentRequest;
import com.messagingappdemo.demo.entity.OtpEmailRequest;
import com.messagingappdemo.demo.entity.PostScheduleEmailRequest;

public class MappingUtil {
    public static EmailRequest mapToEmailRequest(QueueDto queueDto,String subject,String text){
        return new EmailRequest(queueDto.getEmail(),subject,text);
    }

    public static EmailRequest mapToEmailRequest(QueueVerificationMessageDto queueVerificationMessageDto, String subject){
        return new EmailRequest(subject, queueVerificationMessageDto.getMessage());
    }

    public static EmailWithAttachmentRequest mapToEmailWithAttachmentRequest(QueueDto queueDto, String subject, String text){
        return new EmailWithAttachmentRequest(queueDto.getEmail(),subject,text);
    }

    public static OtpEmailRequest mapToOtpEmailRequest(OtpQueueDto queueDto, String subject, String text, String otp){
        return new OtpEmailRequest(queueDto.getEmail(), subject, text, otp);
    }

    public static PostScheduleEmailRequest mapToPostScheduleEmailRequest(PostScheduleDto postScheduleDto, String subject, String text, String post){
        return new PostScheduleEmailRequest(postScheduleDto.getEmail(), subject, text, post);
    }

    public static WhatsAppRequest mapToWhatsAppRequest(QueueDto queueDto, String text){
        return new WhatsAppRequest(queueDto.getPhoneNumber(), queueDto.getFirstName(),text);
    }
}
