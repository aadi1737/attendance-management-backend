package com.nvs.Mavli.controller;

import com.nvs.Mavli.dto.StudentRequestDTO;
import com.nvs.Mavli.dto.StudentResponseDTO;
import com.nvs.Mavli.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody StudentRequestDTO request) {
        return ResponseEntity.ok(studentService.createStudent(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable UUID id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/class")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByClass(
            @RequestParam String className,
            @RequestParam String section) {
        return ResponseEntity.ok(studentService.getStudentsByClass(className, section));
    }

    @GetMapping("/house")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByHouse(
            @RequestParam String house) {
        return ResponseEntity.ok(studentService.getStudentsByHouse(house));
    }
}