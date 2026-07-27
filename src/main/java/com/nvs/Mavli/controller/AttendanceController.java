package com.nvs.Mavli.controller;

import com.nvs.Mavli.dto.*;
import com.nvs.Mavli.entity.SessionType;
import com.nvs.Mavli.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestBody AttendanceMarkRequestDTO request) {
        return ResponseEntity.ok(attendanceService.markAttendance(request));
    }

    @GetMapping("/class-list")
    public ResponseEntity<List<StudentAttendanceStatusDTO>> getClassAttendance(
            @RequestParam String className,
            @RequestParam String section,
            @RequestParam String date,
            @RequestParam SessionType sessionType) {
        return ResponseEntity.ok(attendanceService.getClassAttendance(
                className, section, LocalDate.parse(date), sessionType));
    }

    @GetMapping("/class-summary")
    public ResponseEntity<AttendanceSummaryDTO> getClassSummary(
            @RequestParam String className,
            @RequestParam String section,
            @RequestParam String date,
            @RequestParam SessionType sessionType) {
        return ResponseEntity.ok(attendanceService.getClassSummary(
                className, section, LocalDate.parse(date), sessionType));
    }

    @GetMapping("/house-list")
    public ResponseEntity<List<StudentAttendanceStatusDTO>> getHouseAttendance(
            @RequestParam String house,
            @RequestParam String date,
            @RequestParam SessionType sessionType) {
        return ResponseEntity.ok(attendanceService.getHouseAttendance(house, LocalDate.parse(date), sessionType));
    }

    @GetMapping("/house-summary")
    public ResponseEntity<AttendanceSummaryDTO> getHouseSummary(
            @RequestParam String house,
            @RequestParam String date,
            @RequestParam SessionType sessionType) {
        return ResponseEntity.ok(attendanceService.getHouseSummary(house, LocalDate.parse(date), sessionType));
    }


    @GetMapping("/school-summary")
    public ResponseEntity<AttendanceSummaryDTO> getSchoolSummary(
            @RequestParam String date,
            @RequestParam SessionType sessionType) {
        return ResponseEntity.ok(attendanceService.getSchoolSummary(LocalDate.parse(date), sessionType));
    }

    @GetMapping("/all-class-summaries")
    public ResponseEntity<List<AttendanceSummaryDTO>> getAllClassSummaries(
            @RequestParam String date,
            @RequestParam SessionType sessionType) {
        return ResponseEntity.ok(attendanceService.getAllClassSummaries(LocalDate.parse(date), sessionType));
    }

    @GetMapping("/all-house-summaries")
    public ResponseEntity<List<AttendanceSummaryDTO>> getAllHouseSummaries(
            @RequestParam String date,
            @RequestParam SessionType sessionType) {
        return ResponseEntity.ok(attendanceService.getAllHouseSummaries(LocalDate.parse(date), sessionType));
    }

    @PostMapping("/house-session/mark")
    public ResponseEntity<HouseSessionSummaryDTO> markHouseSession(
            @RequestBody HouseSessionMarkRequestDTO request,
            Authentication authentication) {
        return ResponseEntity.ok(attendanceService.markHouseSession(request, authentication.getName()));
    }

    @GetMapping("/house-session/summary")
    public ResponseEntity<HouseSessionSummaryDTO> getHouseSessionSummary(
            @RequestParam String house,
            @RequestParam String date,
            @RequestParam SessionType sessionType) {
        return ResponseEntity.ok(attendanceService.getHouseSessionSummary(house, LocalDate.parse(date), sessionType));
    }
}
