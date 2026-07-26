package com.nvs.Mavli.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String phone;
    private String password;
}