package com.javaee.projectFroum.projectForum.repositories;

import com.javaee.projectFroum.projectForum.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
