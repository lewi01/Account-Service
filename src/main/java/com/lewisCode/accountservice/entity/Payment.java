package com.lewisCode.accountservice.entity;

import com.lewisCode.accountservice.Validation.PeriodValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(regexp = "\\w+(@acme.com)$", message="Invalid mail it must contain @acme.com")
    @NotBlank(message = "Email must not be null")
    private String employee;
    @PeriodValidation(pattern ="MM-yyyy")
    private String period;
    @Min(value = 1, message = "Salary must be non negative!")
    private Long salary;
    @ManyToOne(cascade = CascadeType.ALL)
    private SignUp signUp;

    public void setSalary(Long salary) {
        Long dollars = salary / 100;
        Long cents = salary % 100;
        this.salary = Long.valueOf(String.format("%d dollar(s) %d cent(s)",dollars,cents));
    }

}
