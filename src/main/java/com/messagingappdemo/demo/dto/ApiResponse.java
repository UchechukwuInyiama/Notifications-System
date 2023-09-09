package com.messagingappdemo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private ResponseStatus status;
    private String code;

    public ApiResponse(ResponseStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    private String message;
    private Object data;

    public ApiResponse(WhatsAppResponse whatsAppResponse) {
    }

    public static ApiResponse getSuccessfulResponse(Object data){
        return new ApiResponse(ResponseStatus.success,ResponseMessages.SUCCESSFUL.getCode(),ResponseMessages.SUCCESSFUL.getMessage(),data);
    }

    public static ApiResponse getFailedResponse(String code, String message){
        return new ApiResponse(ResponseStatus.failed,code,message);
    }
    public static ApiResponse getErrorResponse(String code, String message){
        return new ApiResponse(ResponseStatus.error,code,message);
    }
    public static ApiResponse getNotFoundResponse(String code, String message){
        return new ApiResponse(ResponseStatus.NotFound,code,message);
    }
    public static ApiResponse getInvalidResponse(String code, String message){
        return new ApiResponse(ResponseStatus.Invalid_Request,code,message);
    }
}
