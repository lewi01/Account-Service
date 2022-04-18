package com.lewisCode.accountservice.AuthenticationFailureHandler;

import com.lewisCode.accountservice.enums.Actions;
import com.lewisCode.accountservice.service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomLoginAccessDeniedHandler implements AccessDeniedHandler {
    private final LogService logService;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        String user = request.getUserPrincipal().getName();
        String path = request.getRequestURI();
        logService.createLogs(Actions.ACCESS_DENIED, user, path, path);
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied!");

    }
}
