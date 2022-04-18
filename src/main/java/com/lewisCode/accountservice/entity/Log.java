package com.lewisCode.accountservice.entity;

import com.lewisCode.accountservice.enums.Actions;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private Date date;

    @Enumerated(EnumType.STRING)
    private Actions action;

    @Column
    private String subject;

    @Column
    private String object;

    @Column
    private String path;
}
