package com.lewisCode.accountservice.DTOs;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ChangePassword {
    @Size(min = 12,message = "The password length must be at least 12 chars!")
    private String password;
}
