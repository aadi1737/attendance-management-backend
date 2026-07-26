package com.nvs.Mavli.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class StudentAttendanceStatusDTO {
    private UUID studentId;
    private String name;
    private String rollNo;
    private String photoUrl;
    private String status;   // PRESENT / ABSENT / ON_LEAVE / NOT_MARKED
}