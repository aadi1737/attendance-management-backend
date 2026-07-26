package com.nvs.Mavli.controller;

import com.nvs.Mavli.dto.LeaveRequestDTO;
import com.nvs.Mavli.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping
    public ResponseEntity<String> applyLeave(@RequestBody LeaveRequestDTO request) {
        return ResponseEntity.ok(leaveService.applyLeave(request));
    }
}