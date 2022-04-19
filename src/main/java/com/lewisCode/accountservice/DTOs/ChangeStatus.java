package com.lewisCode.accountservice.DTOs;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ChangeStatus {
    @Email
    @NotBlank
    private String user;
    @Pattern(regexp = "LOCK|UNLOCK")
    private String operation;

    public String getOperationCapitalized(){
        return this.operation.substring(0, 1).toUpperCase() + this.operation.substring(1).toLowerCase();
    }


}
