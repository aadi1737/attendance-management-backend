package com.nvs.Mavli.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "house_session_attendance",
        uniqueConstraints = @UniqueConstraint(columnNames = {"house", "date", "session_type"}))
@Data
public class HouseSessionAttendance {

    @Id
    @GeneratedValue
    private UUID id;

    private String house;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private SessionType sessionType;

    private int presentCount;
    private int absentCount;

    @ManyToOne
    @JoinColumn(name = "marked_by")
    private UserEntity markedBy;
}