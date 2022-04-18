package com.lewisCode.accountservice.entity;

import com.lewisCode.accountservice.enums.Roles;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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

    @Column
    private boolean isEnabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payment;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    Set<Roles> roles = new HashSet<>();

    @Column
    private boolean accountNonLocked;

    @Column
    private int failedAttempt;

    @Column()
    private Date lockTime;

    public void removeRole(Roles role) {
        this.roles.remove(role);
    }
    public void addRole(Roles role) {
        this.roles.add(role);
    }
    public boolean hasRole(Roles role) {
        return roles.contains(role);
    }
    public Set<String> getRolesString() {
        return roles.stream().map(Enum::name).collect(Collectors.toSet());
    }
}
