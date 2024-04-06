package com.hacknc.uncc.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String messsage;
    private List<String> errors;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
