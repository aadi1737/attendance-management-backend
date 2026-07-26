package com.nvs.Mavli.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class LeaveRequestDTO {
    private UUID studentId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private UUID approvedById;
}