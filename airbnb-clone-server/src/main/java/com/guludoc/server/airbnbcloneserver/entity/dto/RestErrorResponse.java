package com.guludoc.server.airbnbcloneserver.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RestErrorResponse {
    private String code;
    private String message;
    private String details;

    public static RestErrorResponse of(String code, String message) {
        return new RestErrorResponse(code, message, "");
    }

    public static RestErrorResponse of(String code, String message, String details) {
        return new RestErrorResponse(code, message, details);
    }
}
