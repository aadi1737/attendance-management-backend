package com.nvs.Mavli.dto;

import lombok.Data;

@Data
public class StudentRequestDTO {
    private String name;
    private String className;
    private String section;
    private String house;
    private String rollNo;
    private String parentPhone;
    private String address;
    private String photoUrl;
}