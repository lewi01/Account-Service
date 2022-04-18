package com.lewisCode.accountservice.AuthenticationFailureHandler;

import com.lewisCode.accountservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomLoginSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        userService.loginAttemptsCount(event.getAuthentication().getName(),0);
    }
}
