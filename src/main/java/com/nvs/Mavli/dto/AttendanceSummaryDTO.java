package com.nvs.Mavli.dto;

import lombok.Data;

@Data
public class AttendanceSummaryDTO {
    private String groupName;   // e.g. "6A" or "Aravali House"
    private int totalStudents;
    private int present;
    private int absent;
    private int onLeave;

    private String assignedTeacherName;  // class teacher (class summary ke liye) ya house master (house summary ke liye)
}