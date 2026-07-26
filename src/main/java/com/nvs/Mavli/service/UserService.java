package com.nvs.Mavli.service;

import com.nvs.Mavli.dto.UserRequestDTO;
import com.nvs.Mavli.dto.UserResponseDTO;
import com.nvs.Mavli.entity.UserEntity;
import com.nvs.Mavli.entity.UserRoleMapping;
import com.nvs.Mavli.repository.UserRepository;
import com.nvs.Mavli.repository.UserRoleMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserRequestDTO request) {
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        if (request.getRoles() != null) {
            for (UserRequestDTO.RoleAssignment ra : request.getRoles()) {
                UserRoleMapping mapping = new UserRoleMapping();
                mapping.setUser(savedUser);
                mapping.setRole(ra.getRole());
                mapping.setRefValue(ra.getRefValue());
                userRoleMappingRepository.save(mapping);
            }
        }

        return mapToResponse(savedUser);
    }

    private UserResponseDTO mapToResponse(UserEntity user) {
        List<UserRoleMapping> mappings = userRoleMappingRepository.findByUserId(user.getId());
        List<String> roles = mappings.stream().map(m -> m.getRole().name()).collect(Collectors.toList());

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setRoles(roles);
        return dto;
    }
}