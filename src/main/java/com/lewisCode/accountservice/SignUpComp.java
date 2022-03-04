package com.lewisCode.accountservice;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class SignUpComp {

    private final SignUp signUp = new SignUp();

    public SignUp postUsers(@NotNull SignUp signUp1){
        signUp.setName(signUp1.getName());
        signUp.setLastName(signUp1.getLastName());
        signUp.setEmail(signUp1.getEmail());
        signUp.setPassword(signUp1.getPassword());
        return signUp;
    }
}
