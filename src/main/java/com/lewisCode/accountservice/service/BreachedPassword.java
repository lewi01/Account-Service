package com.lewisCode.accountservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class BreachedPassword {

    List<String> breachedPasswords = Collections.synchronizedList(List.of("PasswordForJanuary",
            "PasswordForFebruary", "PasswordForMarch", "PasswordForApril", "PasswordForMay", "PasswordForJune",
            "PasswordForJuly", "PasswordForAugust", "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember",
            "PasswordForDecember"));
    private final BCryptPasswordEncoder encoder;

    public boolean isBreached(String password){
        return breachedPasswords.contains(password);
    }
}
