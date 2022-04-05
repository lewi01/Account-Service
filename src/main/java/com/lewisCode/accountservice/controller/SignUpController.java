package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.DTOs.ChangePassword;
import com.lewisCode.accountservice.entity.MySignUpDetailService;
import com.lewisCode.accountservice.entity.SignUp;
import com.lewisCode.accountservice.exeptions.BreachedPasswordException;
import com.lewisCode.accountservice.exeptions.SamePasswordException;
import com.lewisCode.accountservice.exeptions.UserExistException;
import com.lewisCode.accountservice.service.BreachedPassword;
import com.lewisCode.accountservice.service.SignUpServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class SignUpController {

    private final SignUpServiceImp signUpServiceImp;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BreachedPassword breachedPassword;

    @PostMapping("/auth/signup")
    public ResponseEntity<SignUp> signUp(@Valid @RequestBody SignUp signUp){
        if (signUpServiceImp.getUserByEmail(signUp.getEmail()).isPresent()){
            throw new UserExistException("User Exist!");
        }
        if (breachedPassword.isBreached(signUp.getPassword())){
            throw new BreachedPasswordException("The password is in the hacker's database");
        }
        signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));
        return ResponseEntity.ok(signUpServiceImp.registration(signUp)) ;
    }
    @PostMapping("/auth/changepass")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword changePassword,
                                            @AuthenticationPrincipal MySignUpDetailService
                                                    mySignUpDetailService){
            if (breachedPassword.isBreached(changePassword.getPassword())){
                throw new BreachedPasswordException("The password is in the hacker's database");
            }
            if (passwordEncoder.matches(changePassword.getPassword(),
                    mySignUpDetailService.getPassword())){
                throw new SamePasswordException("The passwords must be different");
            }
            Optional<SignUp> signUp =
                    signUpServiceImp.getUserByEmail(mySignUpDetailService.getUsername());
            String mail ="";
            if (signUp.isPresent()){
              signUp.get().setPassword(passwordEncoder.encode(changePassword.getPassword()));
              signUpServiceImp.registration(signUp.get());
              mail = signUp.get().getEmail();
            }
            return  ResponseEntity.ok(Map.of("email:",mail ,
                    "status:", "The password has been updated successfully"));
    }
}
