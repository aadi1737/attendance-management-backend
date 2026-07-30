package com.nvs.Mavli.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class LoginResponseDTO {
    private String token;
    private UUID userId;
    private String name;
    private List<RoleInfoDTO> roles;   // e.g. ["TEACHER", "HOUSE_MASTER"]
}