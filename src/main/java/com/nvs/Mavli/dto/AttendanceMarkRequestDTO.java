package com.nvs.Mavli.dto;

import com.nvs.Mavli.entity.AttendanceStatus;
import com.nvs.Mavli.entity.SessionType;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class AttendanceMarkRequestDTO {
    private SessionType sessionType;
    private LocalDate date;
    private List<StudentAttendanceEntry> students;

    @Data
    public static class StudentAttendanceEntry {
        private UUID studentId;
        private AttendanceStatus status;
    }
}