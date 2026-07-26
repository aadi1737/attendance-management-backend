package com.nvs.Mavli.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

    @Entity
    @Table(name = "leave_records")
    @Data
    public class Leave {

        @Id
        @GeneratedValue
        private UUID id;

        @ManyToOne
        @JoinColumn(name = "student_id", nullable = false)
        private Student student;

        private LocalDate fromDate;
        private LocalDate toDate;

        private String reason;

        @ManyToOne
        @JoinColumn(name = "approved_by")
        private UserEntity approvedBy;
    }