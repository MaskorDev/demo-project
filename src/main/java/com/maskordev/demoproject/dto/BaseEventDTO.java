package com.maskordev.demoproject.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseEventDTO {
    private int orderId;
    private int employerId;
    private LocalDateTime date;
}
