package com.nvs.Mavli.repository;

import com.nvs.Mavli.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeaveRepository extends JpaRepository<Leave, UUID> {
    @Query("SELECT l FROM Leave l WHERE l.student.id = :studentId AND :date BETWEEN l.fromDate AND l.toDate")
    List<Leave> findActiveLeave(@Param("studentId") UUID studentId, @Param("date") LocalDate date);
    //                              ^^^ Add this              ^^^ Add this
}