package com.nvs.Mavli.repository;

import com.nvs.Mavli.entity.Role;
import com.nvs.Mavli.entity.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, UUID> {
    List<UserRoleMapping> findByUserId(UUID userId);

    List<UserRoleMapping> findByRoleIn(List<Role> roles);

    List<UserRoleMapping> findByRoleAndRefValue(Role role, String refValue);
}