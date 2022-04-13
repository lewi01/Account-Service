package com.lewisCode.accountservice.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiveRoles {
    private String user;
    private String role;
    private String operation;
}
