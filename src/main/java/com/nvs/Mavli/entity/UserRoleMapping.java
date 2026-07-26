package com.nvs.Mavli.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "user_role_mapping")
@Data
public class UserRoleMapping {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Agar role = TEACHER -> ye classId hoga (e.g. "6A")
    // Agar role = HOUSE_MASTER -> ye houseName hoga (e.g. "Aravali House")
    private String refValue;
}
