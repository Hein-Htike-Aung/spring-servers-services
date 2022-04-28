package com.example.serverservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    protected LocalDateTime timeStamp;
    protected Map<?, ?> data;
    protected String message;
    protected HttpStatus httpStatus;
    protected int statusCode;
    protected String developerMessage;
    protected String reason;

}
