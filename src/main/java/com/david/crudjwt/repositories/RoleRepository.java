package com.david.crudjwt.repositories;

import com.david.crudjwt.models.securitymodels.ERole;
import com.david.crudjwt.models.securitymodels.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>
{
    Optional<Role> findByRoleName(ERole name);
}
