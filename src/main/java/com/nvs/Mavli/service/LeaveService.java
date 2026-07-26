package com.nvs.Mavli.service;

import com.nvs.Mavli.dto.LeaveRequestDTO;
import com.nvs.Mavli.entity.Leave;
import com.nvs.Mavli.entity.Student;
import com.nvs.Mavli.entity.UserEntity;
import com.nvs.Mavli.repository.LeaveRepository;
import com.nvs.Mavli.repository.StudentRepository;
import com.nvs.Mavli.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public String applyLeave(LeaveRequestDTO request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Leave leave = new Leave();
        leave.setStudent(student);
        leave.setFromDate(request.getFromDate());
        leave.setToDate(request.getToDate());
        leave.setReason(request.getReason());

        if (request.getApprovedById() != null) {
            UserEntity approvedBy = userRepository.findById(request.getApprovedById())
                    .orElseThrow(() -> new RuntimeException("Approver not found"));
            leave.setApprovedBy(approvedBy);
        }

        leaveRepository.save(leave);
        return "Leave applied successfully";
    }
}