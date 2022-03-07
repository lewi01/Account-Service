package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.entity.SignUp;
import com.lewisCode.accountservice.errors.UserExistException;
import com.lewisCode.accountservice.service.SignUpServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class SignUpController {

    private final SignUpServiceImp signUpServiceImp;

    public SignUpController( SignUpServiceImp signUpServiceImp) {
        this.signUpServiceImp = signUpServiceImp;

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUp signUp){
        return ResponseEntity.ok(signUpServiceImp.registration(signUp)) ;
    }


}
