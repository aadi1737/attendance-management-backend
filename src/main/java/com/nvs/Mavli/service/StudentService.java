package com.nvs.Mavli.service;

import com.nvs.Mavli.dto.StudentRequestDTO;
import com.nvs.Mavli.dto.StudentResponseDTO;
import com.nvs.Mavli.entity.Role;
import com.nvs.Mavli.entity.Student;
import com.nvs.Mavli.entity.UserEntity;
import com.nvs.Mavli.entity.UserRoleMapping;
import com.nvs.Mavli.repository.StudentRepository;
import com.nvs.Mavli.repository.UserRepository;
import com.nvs.Mavli.repository.UserRoleMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final  UserRepository userRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;

    public StudentResponseDTO createStudentByAD(StudentRequestDTO request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setClassName(request.getClassName());
        student.setSection(request.getSection());
        student.setHouse(request.getHouse());
        student.setRollNo(request.getRollNo());
        student.setParentPhone(request.getParentPhone());
        student.setAddress(request.getAddress());
        student.setPhotoUrl(request.getPhotoUrl());

        Student saved = studentRepository.save(student);

        return mapToResponse(saved);
    }

    private StudentResponseDTO mapToResponse(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setClassName(student.getClassName());
        dto.setSection(student.getSection());
        dto.setHouse(student.getHouse());
        dto.setRollNo(student.getRollNo());
        dto.setParentPhone(student.getParentPhone());
        dto.setAddress(student.getAddress());
        dto.setPhotoUrl(student.getPhotoUrl());
        return dto;
    }
    public StudentResponseDTO getStudentById(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return mapToResponse(student);
    }

    public List<StudentResponseDTO> getStudentsByClass(String className, String section) {
        return studentRepository.findByClassNameAndSection(className, section)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<StudentResponseDTO> getStudentsByHouse(String house) {
        return studentRepository.findByHouse(house)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public StudentResponseDTO createStudent(StudentRequestDTO request, String teacherPhone) {

        UserEntity classTeacher = userRepository.findByPhone(teacherPhone)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Student student = new Student();
        student.setName(request.getName());
        student.setClassName(request.getClassName());
        student.setSection(request.getSection());
        student.setHouse(request.getHouse());
        student.setRollNo(request.getRollNo());
        student.setParentPhone(request.getParentPhone());
        student.setAddress(request.getAddress());
        student.setPhotoUrl(request.getPhotoUrl());

        student.setClassTeacher(classTeacher);   // 👈 auto-set from JWT

        // House master auto-find karo house naam se
        List<UserRoleMapping> houseMasterMappings = userRoleMappingRepository
                .findByRoleAndRefValue(Role.HOUSE_MASTER, request.getHouse());
        if (!houseMasterMappings.isEmpty()) {
            student.setHouseMaster(houseMasterMappings.get(0).getUser());   // 👈 auto-set house master
        }

        Student saved = studentRepository.save(student);
        return mapToResponse(saved);
    }

    public StudentResponseDTO updatePhoto(UUID studentId, String photoUrl) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setPhotoUrl(photoUrl);
        Student saved = studentRepository.save(student);
        return mapToResponse(saved);
    }
}