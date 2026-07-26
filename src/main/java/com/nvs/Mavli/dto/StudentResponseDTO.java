package com.nvs.Mavli.dto;


import lombok.Data;
import java.util.UUID;

@Data
public class StudentResponseDTO {
    private UUID id;
    private String name;
    private String className;
    private String section;
    private String house;
    private String rollNo;
    private String parentPhone;
    private String address;
    private String photoUrl;
}