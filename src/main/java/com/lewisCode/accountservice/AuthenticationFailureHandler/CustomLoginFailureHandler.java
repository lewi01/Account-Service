package com.lewisCode.accountservice.AuthenticationFailureHandler;

import com.lewisCode.accountservice.entity.User;
import com.lewisCode.accountservice.enums.Actions;
import com.lewisCode.accountservice.enums.Roles;
import com.lewisCode.accountservice.service.LogService;
import com.lewisCode.accountservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomLoginFailureHandler implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final UserService userService;
    private final LogService logService;
    private final HttpServletRequest httpServletRequest;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = Optional.of(event.getAuthentication().getName()).orElse("Anonymous");
        String path = httpServletRequest.getRequestURI();
        logService.createLogs(Actions.LOGIN_FAILED,username,path,path);
        Optional<User> user = userService.getUserByEmail(username);
        if (user.isPresent() && !user.get().hasRole(Roles.ADMINISTRATION)){
            int attempts = user.get().getFailedAttempt() + 1;
            if (attempts >= 5) {
                userService.changeUserAccountNonLocked(user.get().getEmail(),false);
                logService.createLogs(Actions.BRUTE_FORCE,username,path,path);
                logService.createLogs(Actions.LOCK_USER,username,"LOCK USER" + username,
                        "/api/admin/user/access");
            }
            attempts=0;
            userService.loginAttemptsCount(user.get().getEmail(),attempts);
        }
    }
}
