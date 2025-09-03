package com.example.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class AssignTaskRequest {
    private List<Long> userIds;
    private Long leaderId;
    private String taskTitle;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
}

