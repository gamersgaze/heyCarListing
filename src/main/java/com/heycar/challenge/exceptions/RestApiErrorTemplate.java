package com.heycar.challenge.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heycar.challenge.utils.HeyCarUtil;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class RestApiErrorTemplate {

    private final int status;

    private String errorMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = HeyCarUtil.RESPONSE_DATE_TIME_FORMAT)
    private Date timestamp;

    public RestApiErrorTemplate(HttpStatus status) {
        this.status = status.value();
        this.timestamp = new Date();
        this.timestamp = new Date();
    }

    public int getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
