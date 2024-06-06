package com.datn.repository;

import com.datn.entity.MdlRoleAssignmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAssignmentsRepository extends JpaRepository<MdlRoleAssignmentsEntity, Long> {
}
