package com.nvs.Mavli.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String phone;
    private List<String> roles;
}