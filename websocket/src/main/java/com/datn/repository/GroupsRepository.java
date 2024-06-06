package com.datn.repository;

import com.datn.entity.MdlGroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends JpaRepository<MdlGroupsEntity, Long> {
}
