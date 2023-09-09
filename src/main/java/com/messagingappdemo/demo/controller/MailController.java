package com.messagingappdemo.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messagingappdemo.demo.dto.ApiResponse;
import com.messagingappdemo.demo.dto.ResponseMessages;
import com.messagingappdemo.demo.dto.ResponseStatus;
import com.messagingappdemo.demo.dto.WhatsAppRequest;
import com.messagingappdemo.demo.entity.EmailRequest;
import com.messagingappdemo.demo.entity.EmailWithAttachmentRequest;
import com.messagingappdemo.demo.entity.OtpEmailRequest;
import com.messagingappdemo.demo.entity.PostScheduleEmailRequest;
import com.messagingappdemo.demo.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/mail")
public class MailController {

    private final MailService mailService;

    private final TemplateEngine templateEngine;

    private final ObjectMapper mapper;

    @PostMapping("email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest, HttpServletRequest request){
        String appID = request.getHeader("AppID");

        if(!appID.equals("1234")){
           return new ResponseEntity<>(ApiResponse.getFailedResponse("989","Invalid AppID"), HttpStatus.EXPECTATION_FAILED);
        }

        ApiResponse apiResponse = mailService.sendEmail(emailRequest);

        if(!apiResponse.getStatus().equals(ResponseStatus.success)){
            return new ResponseEntity<>(ApiResponse.getFailedResponse(ResponseMessages.ERROR.getCode(), ResponseMessages.ERROR.getMessage()), HttpStatus.EXPECTATION_FAILED);
        }

return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @PostMapping("whatsApp")
    public ResponseEntity<?> sendWhatAppMessage(@RequestBody WhatsAppRequest whatsAppRequest){
        ApiResponse apiResponse = mailService.sendWhatsAppMessage(whatsAppRequest);

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @PostMapping("emailWithAttachment")
   public ResponseEntity<?>sendEmailWithAttachment(@RequestBody EmailWithAttachmentRequest emailWithAttachmentRequest) throws MessagingException {
      mailService.sendEmailWithAttachment(emailWithAttachmentRequest);
        return new ResponseEntity<>("yh",HttpStatus.OK);
   }

    @PostMapping("otpEmail")
    public ResponseEntity<?>sendOtpEmail(@RequestBody OtpEmailRequest otpEmailRequest) throws MessagingException {
        ApiResponse apiResponse = mailService.sendOtpEmail(otpEmailRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("postScheduleEmail")
    public ResponseEntity<?>sendPostScheduleEmail(@RequestBody PostScheduleEmailRequest postScheduleEmailRequest) throws MessagingException {
        ApiResponse apiResponse = mailService.sendPostScheduleEmail(postScheduleEmailRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
