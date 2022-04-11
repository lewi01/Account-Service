package com.lewisCode.accountservice.DTOs;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUser {
    @NonNull
    @NotBlank(message = "Name must not be null.")
    private String name;

    @NotBlank(message = "Lastname must not be null.")
    private String lastname;

    @Email(regexp = "[\\w.]+(@acme.com)$",message = "You must enter a valid email" +
            " or Email must be from acme.com domain")
    @NotBlank(message = "Email must not be null!")
    private String email;

    @NotBlank
    @Size(min = 12, message = "The password length must be at least 12 chars!")
    private String password;

}
