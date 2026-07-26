package com.nvs.Mavli.repository;


import com.nvs.Mavli.entity.Attendance;
import com.nvs.Mavli.entity.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    Optional<Attendance> findByStudentIdAndDateAndSessionType(UUID studentId, LocalDate date, SessionType sessionType);

    List<Attendance> findByStudentIdInAndDateAndSessionType(List<UUID> studentIds, LocalDate date, SessionType sessionType);

    List<Attendance> findByDateAndSessionType(LocalDate date, SessionType sessionType);

    List<Attendance> findByStudent_ClassNameAndStudent_SectionAndDateAndSessionType(
            String className, String section, LocalDate date, SessionType sessionType);

    List<Attendance> findByStudent_HouseAndDateAndSessionType(
            String house, LocalDate date, SessionType sessionType);
}
