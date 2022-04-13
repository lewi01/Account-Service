package com.lewisCode.accountservice.DTOs;

import com.lewisCode.accountservice.entity.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUser {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private List<String> roles;

    public GetUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.roles = user.getRolesString().stream()
                .sorted().collect(Collectors.toList());
    }
}
