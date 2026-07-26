package com.nvs.Mavli.entity;

import jakarta.persistence.*;
        import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "attendance",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "date", "session_type"})
)
@Data
public class Attendance {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private SessionType sessionType;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @ManyToOne
    @JoinColumn(name = "marked_by")
    private UserEntity markedBy;
}
