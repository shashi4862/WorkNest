package com.example.dto;

import com.example.enums.TaskStatus;
import lombok.Data;

@Data
public class UpdateStatusRequest {
    private Long assignmentId;
    private TaskStatus status;
}

