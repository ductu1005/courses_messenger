package com.datn.repository;

import com.datn.entity.MdlGroupsMembersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsMembersRepository extends JpaRepository<MdlGroupsMembersEntity, Long> {
}
