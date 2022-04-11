package com.lewisCode.accountservice.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

import java.util.List;


@Entity(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    private String name;
    @Column
    private String lastname;
    @Email
    @Column
    private String email;
    @Column
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payment;

//   private Set<Roles> roles;
}
