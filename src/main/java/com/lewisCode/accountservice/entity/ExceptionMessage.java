package com.lewisCode.accountservice.entity;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionMessage {
    private LocalDateTime timestamp;
    private int status;
    private HttpStatus error;
    private String message;
    private String path;

}
