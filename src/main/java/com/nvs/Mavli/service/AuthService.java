package com.nvs.Mavli.service;

import com.nvs.Mavli.dto.LoginRequestDTO;
import com.nvs.Mavli.dto.LoginResponseDTO;
import com.nvs.Mavli.entity.UserEntity;
import com.nvs.Mavli.entity.UserRoleMapping;
import com.nvs.Mavli.repository.UserRepository;
import com.nvs.Mavli.repository.UserRoleMappingRepository;
import com.nvs.Mavli.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO request) {
        UserEntity user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("Invalid phone or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid phone or password");
        }

        List<UserRoleMapping> mappings = userRoleMappingRepository.findByUserId(user.getId());
        List<String> roles = mappings.stream()
                .map(m -> m.getRole().name())
                .collect(Collectors.toList());

        String token = jwtService.generateToken(user.getPhone());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setRoles(roles);

        return response;
    }
}