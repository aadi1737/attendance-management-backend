package com.nvs.Mavli.repository;

import com.nvs.Mavli.entity.HouseSessionAttendance;
import com.nvs.Mavli.entity.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface HouseSessionAttendanceRepository extends JpaRepository<HouseSessionAttendance, UUID> {
    Optional<HouseSessionAttendance> findByHouseAndDateAndSessionType(String house, LocalDate date, SessionType sessionType);
}