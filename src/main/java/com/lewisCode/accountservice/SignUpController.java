package com.lewisCode.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class SignUpController {

    private final SignUpComp signUpComponent;
    @Autowired
    public SignUpController(SignUpComp signUpComponent) {
        this.signUpComponent = signUpComponent;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUp signUp,
                                         HttpServletRequest httpServletRequest){
        final
        LocalDate date = LocalDate.now();
        final Integer status = HttpStatus.BAD_REQUEST.value();
        final String error = HttpStatus.BAD_REQUEST.toString();
        final String path = httpServletRequest.getRequestURI();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", date);
        response.put("status",  status);
        response.put("error", error);
        response.put("path", path);
        try {
            return ResponseEntity.ok(signUpComponent.postUsers(signUp));
        }catch (IllegalAccessError e){
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

}
