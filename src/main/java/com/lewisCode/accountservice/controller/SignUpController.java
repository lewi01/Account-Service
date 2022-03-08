package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.entity.SignUp;
import com.lewisCode.accountservice.errors.UserExistException;
import com.lewisCode.accountservice.service.SignUpServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class SignUpController {

    private final SignUpServiceImp signUpServiceImp;
    private final PasswordEncoder passwordEncoder;

    public SignUpController(SignUpServiceImp signUpServiceImp, PasswordEncoder passwordEncoder) {
        this.signUpServiceImp = signUpServiceImp;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUp> signUp(@Valid @RequestBody SignUp signUp){
        if (signUpServiceImp.getUserByEmail(signUp.getEmail()).isPresent()){
            throw new UserExistException();
        }
        signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));
        return ResponseEntity.ok(signUpServiceImp.registration(signUp)) ;
    }


}
