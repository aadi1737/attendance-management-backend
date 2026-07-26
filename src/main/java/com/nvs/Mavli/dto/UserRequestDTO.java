package com.nvs.Mavli.dto;

import com.nvs.Mavli.entity.Role;
import lombok.Data;
import java.util.List;

@Data
public class UserRequestDTO {
    private String name;
    private String phone;
    private String password;
    private List<RoleAssignment> roles;

    @Data
    public static class RoleAssignment {
        private Role role;       // TEACHER / HOUSE_MASTER / PRINCIPAL / ADMIN
        private String refValue; // e.g. "8A" for teacher, "Aravali House" for house master
    }
}