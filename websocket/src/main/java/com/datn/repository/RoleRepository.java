package com.datn.repository;

import com.datn.entity.MdlRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<MdlRoleEntity, Long> {
}
