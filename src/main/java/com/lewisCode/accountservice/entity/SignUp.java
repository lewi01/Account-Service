package com.lewisCode.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sign_up")
public class SignUp {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String lastname;
    @NotNull
    @NotEmpty
    @Email(regexp = "\\w+(@acme.com)$", message="Invalid mail")
    private String email;
    @NotNull
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

//    private String role;
}
