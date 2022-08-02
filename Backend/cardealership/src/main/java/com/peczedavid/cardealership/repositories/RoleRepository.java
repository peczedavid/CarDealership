package com.peczedavid.cardealership.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peczedavid.cardealership.models.ERole;
import com.peczedavid.cardealership.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(ERole eRole);
}