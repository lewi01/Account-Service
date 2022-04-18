package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.entity.Log;
import com.lewisCode.accountservice.enums.Actions;
import com.lewisCode.accountservice.repository.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public Log createLogs(Actions action, String subject, String object, String path){
        Log log1 = new Log();
        log1.setDate(new Date(System.currentTimeMillis()));
        log1.setAction(action);
        log1.setSubject(subject);
        log1.setObject(object);
        log1.setPath(path);
        return logRepository.save(log1);
    }
    public List<Log> allLogs(){
        return logRepository.findAll();
    }
}
