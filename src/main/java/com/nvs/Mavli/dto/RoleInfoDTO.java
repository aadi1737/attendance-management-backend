package com.nvs.Mavli.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.List;// naya DTO
import java.util.stream.Collectors;

@Data
public class RoleInfoDTO {
    private String role;
    private String refValue;
}

//private Arrays mappings;
//// AuthService.login() mein change
//List<RoleInfoDTO> roles = mappings.stream().map(m -> {
//    RoleInfoDTO r = new RoleInfoDTO();
//    r.setRole(m.getRole().name());
//    r.setRefValue(m.getRefValue());
//    return r;
//}).collect(Collectors.toList());