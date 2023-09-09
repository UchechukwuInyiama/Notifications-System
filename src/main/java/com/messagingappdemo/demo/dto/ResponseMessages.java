package com.messagingappdemo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessages {
    SUCCESSFUL("01","Request Successful"),
    FAILED("03","Request Failed"),
    ERROR("00","Invalid Request"),
    NOT_FOUND("09","NOT FOUND"),
    NOT_ALLOWED("05","NOT ALLOWED"),
    INTERNAL_SERVER_ERROR("06","Internal Server Error"),
   INVALID ("08","Invalid Resource Passed as Parameter") ;
    private String code;
    private String message;
}
