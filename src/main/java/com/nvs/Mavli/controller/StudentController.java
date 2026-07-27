package com.nvs.Mavli.controller;

import com.nvs.Mavli.dto.StudentRequestDTO;
import com.nvs.Mavli.dto.StudentResponseDTO;
import com.nvs.Mavli.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/byAD")
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody StudentRequestDTO request) {
        return ResponseEntity.ok(studentService.createStudentByAD(request));
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

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @RequestBody StudentRequestDTO request,
            Authentication authentication) {
        String phone = authentication.getName();  // JWT filter se already set ho raha hai
        return ResponseEntity.ok(studentService.createStudent(request, phone));
    }

    @PatchMapping("/{id}/photo")
    public ResponseEntity<StudentResponseDTO> updatePhoto(
            @PathVariable UUID id,
            @RequestParam String photoUrl) {
        return ResponseEntity.ok(studentService.updatePhoto(id, photoUrl));
    }
}