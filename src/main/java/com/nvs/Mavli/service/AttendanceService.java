package com.nvs.Mavli.service;

import com.nvs.Mavli.dto.AttendanceMarkRequestDTO;
import com.nvs.Mavli.dto.AttendanceSummaryDTO;
import com.nvs.Mavli.dto.StudentAttendanceStatusDTO;
import com.nvs.Mavli.entity.*;
import com.nvs.Mavli.repository.AttendanceRepository;
import com.nvs.Mavli.repository.LeaveRepository;
import com.nvs.Mavli.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final LeaveRepository leaveRepository;

    public String markAttendance(AttendanceMarkRequestDTO request) {
        for (AttendanceMarkRequestDTO.StudentAttendanceEntry entry : request.getStudents()) {
            Student student = studentRepository.findById(entry.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found: " + entry.getStudentId()));

            Attendance attendance = attendanceRepository
                    .findByStudentIdAndDateAndSessionType(entry.getStudentId(), request.getDate(), request.getSessionType())
                    .orElse(new Attendance());

            attendance.setStudent(student);
            attendance.setDate(request.getDate());
            attendance.setSessionType(request.getSessionType());
            attendance.setStatus(entry.getStatus());

            attendanceRepository.save(attendance);
        }
        return "Attendance marked for " + request.getStudents().size() + " students";
    }

    public List<StudentAttendanceStatusDTO> getClassAttendance(String className, String section, LocalDate date, SessionType sessionType) {
        List<Student> students = studentRepository.findByClassNameAndSection(className, section);
        List<Attendance> attendanceList = attendanceRepository
                .findByStudent_ClassNameAndStudent_SectionAndDateAndSessionType(className, section, date, sessionType);

        Map<UUID, AttendanceStatus> statusMap = attendanceList.stream()
                .collect(Collectors.toMap(a -> a.getStudent().getId(), Attendance::getStatus));

        return students.stream().map(s -> {
            StudentAttendanceStatusDTO dto = new StudentAttendanceStatusDTO();
            dto.setStudentId(s.getId());
            dto.setName(s.getName());
            dto.setRollNo(s.getRollNo());
            dto.setPhotoUrl(s.getPhotoUrl());

            boolean onLeave = !leaveRepository.findActiveLeave(s.getId(), date).isEmpty();
            if (onLeave) {
                dto.setStatus("ON_LEAVE");
            } else {
                AttendanceStatus status = statusMap.get(s.getId());
                dto.setStatus(status != null ? status.name() : "NOT_MARKED");
            }
            return dto;
        }).collect(Collectors.toList());
    }

    // ✅ REMOVED the duplicate method - keeping only this one
    public AttendanceSummaryDTO getClassSummary(String className, String section, LocalDate date, SessionType sessionType) {
        List<StudentAttendanceStatusDTO> list = getClassAttendance(className, section, date, sessionType);
        List<Student> students = studentRepository.findByClassNameAndSection(className, section);

        AttendanceSummaryDTO summary = new AttendanceSummaryDTO();
        summary.setGroupName(className + section);
        summary.setTotalStudents(list.size()); // ✅ This is correct because list contains ALL students
        summary.setPresent((int) list.stream().filter(s -> "PRESENT".equals(s.getStatus())).count());
        summary.setAbsent((int) list.stream().filter(s -> "ABSENT".equals(s.getStatus())).count());
        summary.setOnLeave((int) list.stream().filter(s -> "ON_LEAVE".equals(s.getStatus())).count());

        // ✅ Get teacher name from first student's class teacher
        String teacherName = students.stream()
                .findFirst()
                .map(Student::getClassTeacher)
                .filter(Objects::nonNull)
                .map(UserEntity::getName)
                .orElse("Not Assigned");
        summary.setAssignedTeacherName(teacherName);

        return summary;
    }

    public List<StudentAttendanceStatusDTO> getHouseAttendance(String house, LocalDate date, SessionType sessionType) {
        List<Student> students = studentRepository.findByHouse(house);
        List<Attendance> attendanceList = attendanceRepository
                .findByStudent_HouseAndDateAndSessionType(house, date, sessionType);

        Map<UUID, AttendanceStatus> statusMap = attendanceList.stream()
                .collect(Collectors.toMap(a -> a.getStudent().getId(), Attendance::getStatus));

        return students.stream().map(s -> {
            StudentAttendanceStatusDTO dto = new StudentAttendanceStatusDTO();
            dto.setStudentId(s.getId());
            dto.setName(s.getName());
            dto.setRollNo(s.getRollNo());
            dto.setPhotoUrl(s.getPhotoUrl());

            boolean onLeave = !leaveRepository.findActiveLeave(s.getId(), date).isEmpty();
            if (onLeave) {
                dto.setStatus("ON_LEAVE");
            } else {
                AttendanceStatus status = statusMap.get(s.getId());
                dto.setStatus(status != null ? status.name() : "NOT_MARKED");
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public AttendanceSummaryDTO getHouseSummary(String house, LocalDate date, SessionType sessionType) {
        List<StudentAttendanceStatusDTO> list = getHouseAttendance(house, date, sessionType);
        List<Student> students = studentRepository.findByHouse(house);

        AttendanceSummaryDTO summary = new AttendanceSummaryDTO();
        summary.setGroupName(house);
        summary.setTotalStudents(list.size()); // ✅ Correct because list contains ALL students in house
        summary.setPresent((int) list.stream().filter(s -> "PRESENT".equals(s.getStatus())).count());
        summary.setAbsent((int) list.stream().filter(s -> "ABSENT".equals(s.getStatus())).count());
        summary.setOnLeave((int) list.stream().filter(s -> "ON_LEAVE".equals(s.getStatus())).count());

        // ✅ Get house master name from first student's house master
        String houseMasterName = students.stream()
                .findFirst()
                .map(Student::getHouseMaster)
                .filter(Objects::nonNull)
                .map(UserEntity::getName)
                .orElse("Not Assigned");
        summary.setAssignedTeacherName(houseMasterName);

        return summary;
    }

    // ✅ NEW METHOD - Previously missing!
    public AttendanceSummaryDTO getSchoolSummary(LocalDate date, SessionType sessionType) {
        List<Student> allStudents = studentRepository.findAll();
        List<Attendance> attendanceList = attendanceRepository.findByDateAndSessionType(date, sessionType);

        Map<UUID, AttendanceStatus> statusMap = attendanceList.stream()
                .collect(Collectors.toMap(a -> a.getStudent().getId(), Attendance::getStatus));

        AttendanceSummaryDTO summary = new AttendanceSummaryDTO();
        summary.setGroupName("School");
        summary.setTotalStudents(allStudents.size());

        long presentCount = 0;
        long absentCount = 0;
        long onLeaveCount = 0;

        for (Student student : allStudents) {
            boolean onLeave = !leaveRepository.findActiveLeave(student.getId(), date).isEmpty();
            if (onLeave) {
                onLeaveCount++;
            } else {
                AttendanceStatus status = statusMap.get(student.getId());
                if (status == AttendanceStatus.PRESENT) {
                    presentCount++;
                } else if (status == AttendanceStatus.ABSENT) {
                    absentCount++;
                }
                // NOT_MARKED students are not counted in present/absent/onLeave
            }
        }

        summary.setPresent((int) presentCount);
        summary.setAbsent((int) absentCount);
        summary.setOnLeave((int) onLeaveCount);
        summary.setAssignedTeacherName("Principal"); // Or whoever is responsible

        return summary;
    }

    public List<AttendanceSummaryDTO> getAllClassSummaries(LocalDate date, SessionType sessionType) {
        List<Object[]> classSections = studentRepository.findDistinctClassSections();

        return classSections.stream()
                .map(row -> getClassSummary((String) row[0], (String) row[1], date, sessionType))
                .collect(Collectors.toList());
    }

    public List<AttendanceSummaryDTO> getAllHouseSummaries(LocalDate date, SessionType sessionType) {
        List<String> houses = studentRepository.findDistinctHouses();

        return houses.stream()
                .map(house -> getHouseSummary(house, date, sessionType))
                .collect(Collectors.toList());
    }
}