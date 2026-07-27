package com.nvs.Mavli.service;

import com.nvs.Mavli.dto.LeaveRequestDTO;
import com.nvs.Mavli.entity.*;
import com.nvs.Mavli.repository.LeaveRepository;
import com.nvs.Mavli.repository.StudentRepository;
import com.nvs.Mavli.repository.UserRepository;
import com.nvs.Mavli.repository.UserRoleMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;

    public String applyLeave(LeaveRequestDTO request, String requesterPhone) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        UserEntity requester = userRepository.findByPhone(requesterPhone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserRoleMapping> requesterRoles = userRoleMappingRepository.findByUserId(requester.getId());

        boolean isAuthorized = requesterRoles.stream().anyMatch(rm ->
                (rm.getRole() == Role.HOUSE_MASTER && rm.getRefValue().equals(student.getHouse()))
                        || rm.getRole() == Role.PRINCIPAL
                        || rm.getRole() == Role.ADMIN
        );

        if (!isAuthorized) {
            throw new RuntimeException("Only the student's House Master or Principal/Admin can apply leave");
        }

        Leave leave = new Leave();
        leave.setStudent(student);
        leave.setFromDate(request.getFromDate());
        leave.setToDate(request.getToDate());
        leave.setReason(request.getReason());
        leave.setApprovedBy(requester);

        leaveRepository.save(leave);
        return "Leave applied successfully";
    }
}