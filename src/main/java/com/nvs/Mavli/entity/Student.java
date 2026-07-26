package com.nvs.Mavli.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String className;   // e.g. "6"
    private String section;     // e.g. "A"
    private String house;
    private String rollNo;
    private String parentPhone;
    private String address;
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "class_teacher_id")
    private UserEntity classTeacher;

    @ManyToOne
    @JoinColumn(name = "house_master_id")
    private UserEntity houseMaster;
}
