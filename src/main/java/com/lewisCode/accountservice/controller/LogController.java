package com.lewisCode.accountservice.controller;


import com.lewisCode.accountservice.service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LogController {
    private final LogService logService;

    @GetMapping("api/security/events")
    public ResponseEntity<?> getAllLogs(){ return ResponseEntity.ok(logService.allLogs());}
}
