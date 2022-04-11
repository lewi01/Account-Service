package com.lewisCode.accountservice.DTOs;

import com.lewisCode.accountservice.entity.User;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUser {
    private Long id;
    private String name;
    private String lastname;
    private String email;

    public GetUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }
}
