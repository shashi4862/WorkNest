package com.example.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class AssignTaskRequest {
    private List<Long> userIds;
    private Long leaderId;
    private String taskTitle;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
}
