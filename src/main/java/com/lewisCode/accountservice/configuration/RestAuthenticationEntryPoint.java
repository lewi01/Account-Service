package com.lewisCode.accountservice.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        out.print("{\n" +
                "  \"timestamp\": \"" + new Date() + "\",\n" +
                "  \"status\": " + 401 + ",\n" +
                "  \"error\": \"Unauthorized\",\n" +
                "  \"message\": \"User Not registered or wrong email or password\",\n" +
                "  \"path\": \"" + request.getServletPath() + "\"\n" +
                "}");
        out.flush();
        out.close();
    }
}
