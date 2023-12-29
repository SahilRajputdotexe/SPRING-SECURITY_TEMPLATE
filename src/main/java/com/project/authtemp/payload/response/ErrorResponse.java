package com.project.authtemp.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorResponse {

    private int errorCode;
    private String error;
    private String message;

}
