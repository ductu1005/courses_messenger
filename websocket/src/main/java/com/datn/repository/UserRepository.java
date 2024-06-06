package com.datn.repository;

import com.datn.entity.MdlUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MdlUserEntity, Long> {
    Optional<MdlUserEntity> findFirstByUsernameAndPassword(String email, String password);

    MdlUserEntity findFirstByUsername(String username);
}
