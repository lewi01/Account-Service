package com.lewisCode.accountservice.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BreachedPassword {

    public boolean isBreached(String password){
        List<String> breachedPasswords = List.of("PasswordForJanuary",
                "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
                "PasswordForMay", "PasswordForJune",
                "PasswordForJuly", "PasswordForAugust", "PasswordForSeptember",
                "PasswordForOctober", "PasswordForNovember",
                "PasswordForDecember");
        return breachedPasswords.contains(password);
    }
}
